package com.visual.open.anpr.core.models;

import ai.onnxruntime.OrtEnvironment;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TestMain01 {
    //静态加载动态链接库
    static{ nu.pattern.OpenCV.loadShared(); }
    private OrtEnvironment env = OrtEnvironment.getEnvironment();

    public  static  void my_letter_box(Mat image, int imageWidth, int imageHeight){
        int w = image.width();
        int h = image.height();
        double r = Math.min(1.0 * imageHeight / h, 1.0 * imageWidth / w);
        System.out.println(r);
        int new_h = Double.valueOf(h*r).intValue();
        int new_w = Double.valueOf(w*r).intValue();
        int top = Double.valueOf((imageHeight - new_h) / 2.0).intValue();
        int left = Double.valueOf((imageWidth-new_w) / 2.0).intValue();
        System.out.println(top);
        System.out.println(left);

        int bottom = imageHeight - new_h-top ;
        int right = imageWidth - new_w-left ;
        System.out.println(bottom);
        System.out.println(right);

        Mat resizeDst = new Mat();
        Imgproc.resize(image, resizeDst, new Size(new_w,new_h), 0, 0, Imgproc.INTER_AREA);

        Mat res = new Mat();
        Core.copyMakeBorder(resizeDst, res, top, bottom, left, right, Core.BORDER_CONSTANT, new Scalar(114,114,114));

        Imgcodecs.imwrite("res.jpg", res);
    }

    public static void main(String[] args) {
        String imagePath = "open-anpr-core/src/test/resources/images/imagetmp.jpg";

        Mat image = Imgcodecs.imread(imagePath);
        my_letter_box(image, 640, 640);


    }
}
