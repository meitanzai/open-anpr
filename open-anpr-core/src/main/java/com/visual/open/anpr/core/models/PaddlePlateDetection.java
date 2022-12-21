package com.visual.open.anpr.core.models;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtSession;
import com.visual.open.anpr.core.base.BaseOnnxInfer;
import com.visual.open.anpr.core.base.PlateDetection;
import com.visual.open.anpr.core.clipper.Clipper;
import com.visual.open.anpr.core.clipper.ClipperOffset;
import com.visual.open.anpr.core.clipper.Path;
import com.visual.open.anpr.core.clipper.Paths;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;
import com.visual.open.anpr.core.domain.Polygon;
import com.visual.open.anpr.core.utils.ReleaseUtil;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.*;
import java.util.stream.Collectors;

public class PaddlePlateDetection extends BaseOnnxInfer implements PlateDetection {

    private float thresh=0.3f;
    private float box_thresh=0.6f;
    private int max_candidates=1000;
    private float unclip_ratio=1.5f;
    private float min_size = 3f;

    private float[] std = {0.229f, 0.224f, 0.225f};
    private Scalar mean = new Scalar(0.485, 0.456, 0.406);

    public PaddlePlateDetection(String modelPath, int threads) {
        super(modelPath, threads);
    }

    @Override
    public List<PlateInfo> inference(ImageMat image, float scoreTh, float iouTh, Map<String, Object> params) {
        OnnxTensor tensor = null;
        OrtSession.Result output = null;
        ImageMat imageMat = image.clone();
        try {
            //前置处理
            tensor = imageMat
                    .blobFromImageAndDoReleaseMat(1.0/255, this.mean, true)
                    .to4dFloatOnnxTensorAndNoReleaseMat(this.std,true);
            //ONNX推理
            output = getSession().run(Collections.singletonMap(getInputName(), tensor));
            float[][][][] result = (float[][][][]) output.get(0).getValue();
            //模型后处理
            List<PlateInfo> plateInfos = new ArrayList<>();
            for (float[][] item : result[0]){
                List<PlateInfo> list = this.dbPostProcess(item, image.getWidth(), image.getHeight());
                if(!list.isEmpty()){
                    plateInfos.addAll(list);
                }
            }
            //返回
            return plateInfos;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(null != tensor){
                ReleaseUtil.release(tensor);
            }
            if(null != output){
                ReleaseUtil.release(output);
            }
            if(null != imageMat){
                ReleaseUtil.release(imageMat);
            }
        }
    }

    /**
     * 后处理
     * @param pred
     * @param destWidth
     * @param destHeight
     * @return
     */
    private List<PlateInfo> dbPostProcess(float[][] pred, int destWidth, int destHeight){
        //初始化Mat
        Mat rawMat = Mat.zeros(pred.length,pred[0].length, CvType.CV_8UC1);
        Mat maskMat = Mat.zeros(pred.length,pred[0].length, CvType.CV_8UC1);
        int width = rawMat.width(); int height = rawMat.height();
        //添加数据
        for(int i=0; i< pred.length; i++){
            for(int j=0; j<pred[i].length; j++ ){
                rawMat.put(i, j, pred[i][j]);
                maskMat.put(i, j, pred[i][j] >= thresh ? 255 : 0);
            }
        }
        //边缘提取
        final List<MatOfPoint> points = new ArrayList<>();
        final Mat hierarchy = new Mat();
        Imgproc.findContours(maskMat, points, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        //遍历边缘
        List<PlateInfo> list = new ArrayList<>();
        int numContours = Math.min(points.size(), max_candidates);
        for(int i=0; i<numContours; i++){
            MatOfPoint contour = points.get(i);
            PlateInfo res = getMiniBoxes(contour);
            if(res.score < this.min_size){
                continue;
            }
            //获取边缘线的最大最小坐标
            int h = maskMat.size(0);
            int w = maskMat.size(1);
            PlateInfo.Point []box = res.box.toArray();
            int xmin = clip(Arrays.stream(box).mapToInt((item) -> Double.valueOf(Math.floor(item.x)).intValue()).min().getAsInt(), 0, w-1);
            int xmax = clip(Arrays.stream(box).mapToInt((item) -> Double.valueOf(Math.ceil(item.x)).intValue()).max().getAsInt(), 0, w-1);
            int ymin = clip(Arrays.stream(box).mapToInt((item) -> Double.valueOf(Math.floor(item.y)).intValue()).min().getAsInt(), 0, h-1);
            int ymax = clip(Arrays.stream(box).mapToInt((item) -> Double.valueOf(Math.ceil(item.y)).intValue()).max().getAsInt(), 0, h-1);
            Mat mask = Mat.zeros(ymax - ymin + 1,xmax - xmin + 1, CvType.CV_8UC1);
            //组织多边形的蒙版数据
            List<Point> boxPoints = Arrays.stream(box).map((item)-> new Point(item.x, item.y)).collect(Collectors.toList());
            List<Point> boxPointList = Arrays.stream(box).map((item)-> new Point(item.x-xmin, item.y-ymin)).collect(Collectors.toList());
            MatOfPoint matOfPoint = new MatOfPoint();
            matOfPoint.fromList(boxPointList);
            List<MatOfPoint> ptsoo = Collections.singletonList(matOfPoint);
            //数据填充
            Imgproc.fillPoly(mask, ptsoo, Scalar.all(1));
            Scalar scalar = Core.mean(new Mat(rawMat, new Rect( xmin, ymin, xmax + 1 -xmin, ymax + 1-ymin)), mask);
            float score = (float) scalar.val[0];
            if(this.box_thresh > score){
                continue;
            }
            //多边形的距离计算
            Polygon polygon = new Polygon(boxPoints);
            double distance = polygon.getArea() * unclip_ratio / polygon.getLength();
            //线和多边形的裁剪和偏移
            Path path = new Path();
            for(Point u : boxPoints){
                path.add(new com.visual.open.anpr.core.clipper.Point.LongPoint((long) u.x, (long)u.y));
            }
            Paths paths = new Paths();
            ClipperOffset offset = new ClipperOffset();
            offset.addPath(path, Clipper.JoinType.ROUND, Clipper.EndType.CLOSED_POLYGON);
            offset.execute(paths, distance);
            //重新获取边缘线
            MatOfPoint contour1 = new MatOfPoint();
            List<Point> poo= paths.get(0).stream().map(item -> new Point(item.getX(), item.getY())).collect(Collectors.toList());
            contour1.fromList(poo);
            //重新提取边缘线
            PlateInfo res1 = getMiniBoxes(contour1);
            if(res1.score < this.min_size+2){
                continue;
            }
            //添加一点偏移量
            int offsetWidth  = (int) (res1.box.width()  * 0.00f);
            int offsetHeight = (int) (res1.box.height() * 0.00f);
            //组装返回信息
            PlateInfo r = PlateInfo.build(score, PlateInfo.PlateBox.build(
                    PlateInfo.Point.build(
                            clip(Math.round(res1.box.leftTop.x / width * destWidth) - offsetWidth, 0, destWidth),
                            clip(Math.round(res1.box.leftTop.y / height * destHeight) - offsetHeight, 0, destHeight)),
                    PlateInfo.Point.build(
                            clip(Math.round(res1.box.rightTop.x / width * destWidth) + offsetWidth, 0, destWidth),
                            clip(Math.round(res1.box.rightTop.y / height * destHeight) - offsetHeight, 0, destHeight)),
                    PlateInfo.Point.build(
                            clip(Math.round(res1.box.rightBottom.x / width * destWidth) + offsetWidth, 0, destWidth),
                            clip(Math.round(res1.box.rightBottom.y / height * destHeight) + offsetHeight, 0, destHeight)),
                    PlateInfo.Point.build(
                            clip(Math.round(res1.box.leftBottom.x / width * destWidth) - offsetWidth, 0, destWidth),
                            clip(Math.round(res1.box.leftBottom.y / height * destHeight) + offsetHeight, 0, destHeight))
            ));
            list.add(r);
        }

        return list;
    }


    public PlateInfo getMiniBoxes(MatOfPoint contour){
        MatOfPoint2f pts = new MatOfPoint2f();
        pts.fromList(contour.toList());
        RotatedRect rect = Imgproc.minAreaRect(pts);

        Mat yyy = new Mat();
        Imgproc.boxPoints(rect, yyy);

        List<Point> cs = new ArrayList<>();
        for (int m=0; m< yyy.size().height; m++){
            Point ccc = new Point(yyy.get(m, 0)[0], yyy.get(m, 1)[0]);
            cs.add(ccc);
        }
        Collections.sort(cs, Comparator.comparingDouble(o -> o.x));

        int index_1 = 0;
        int index_2 = 1;
        int index_3 = 2;
        int index_4 = 3;

        if(cs.get(1).y > cs.get(0).y){
            index_1 = 0;
            index_4 = 1;
        }else{
            index_1 = 1;
            index_4 = 0;
        }

        if(cs.get(3).y > cs.get(2).y){
            index_2 = 2;
            index_3 = 3;
        }else{
            index_2 = 3;
            index_3 = 2;
        }

        double sside = Math.min(rect.size.height, rect.size.width);
        PlateInfo.PlateBox plateBox = PlateInfo.PlateBox.build(
                PlateInfo.Point.build((float) cs.get(index_1).x, (float)cs.get(index_1).y),
                PlateInfo.Point.build((float) cs.get(index_2).x, (float)cs.get(index_2).y),
                PlateInfo.Point.build((float) cs.get(index_3).x, (float)cs.get(index_3).y),
                PlateInfo.Point.build((float) cs.get(index_4).x, (float)cs.get(index_4).y)
        );
        return PlateInfo.build((float)sside, plateBox);
    }

    public static int clip(int value, int min, int max){
        if(value > max){
            return max;
        }
        if(value < min){
            return min;
        }
        return value;
    }

}
