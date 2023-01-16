package com.visual.open.anpr.core.models;

import com.visual.open.anpr.core.domain.DrawImage;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PaddlePlateDetectionTest {

    public static void main(String[] args) {
        PaddlePlateDetection detection = new PaddlePlateDetection("open-anpr-core/src/main/resources/models/model3.onnx", 1);

        String imagePath = "open-anpr-core/src/test/resources/images/image010.jpg";
        for(int wi=1; wi <=128; wi++){
            for(int wy=3; wy>=0;wy--){
                int width = wi * 32 - wy;
                for(int hi=1; hi <=128; hi++) {
                    for (int hy = 3; hy >= 0; hy--) {
                        int height = hi * 32 - hy;
                        ImageMat imageMat = null;
                        try {
                            imageMat = ImageMat.fromImage(imagePath).resizeAndDoReleaseMat(width, height);
                            List<PlateInfo> plateInfos = detection.inference(imageMat, 1,1, new HashMap<>());
                            System.out.println(plateInfos);
                            System.out.println("success:"+width+":"+height);
                        }catch (Throwable t){
                            System.out.println("error:"+width+":"+height);
                        }finally {
                            if(imageMat != null){
                                imageMat.release();
                            }
                        }
                    }
                }
            }
        }


//        for(int w=351; w <=351; w++){
//            for(int h=351; h <=351; h++){
//                ImageMat imageMat = null;
//                try {
//                    imageMat = ImageMat.fromImage(imagePath).resizeAndDoReleaseMat(w, h);
//                    List<PlateInfo> plateInfos = detection.inference(imageMat, 1,1, new HashMap<>());
//                    System.out.println(plateInfos);
//                    System.out.println("success:"+w+":"+h);
//                }catch (Throwable t){
//                    System.out.println("error:"+w+":"+h);
//                }finally {
//                    if(imageMat != null){
//                        imageMat.release();
//                    }
//                }
//            }
//        }





//        String image1Path = "open-anpr-core/src/test/resources/images/imagetmp.jpg";
//        Imgcodecs.imwrite(image1Path, imageMat.toCvMat());


//        DrawImage drawImage = DrawImage.build(image1Path);
//        for(PlateInfo plateInfo : plateInfos){
//            PlateInfo.Point [] points = plateInfo.box.toArray();
//            for(int i =0; i< points.length; i++){
//                if(i+1 == points.length){
//                    drawImage.drawLine(
//                            new DrawImage.Point((int)points[i].x, (int)points[i].y),
//                            new DrawImage.Point((int)points[0].x, (int)points[0].y),
//                            2, Color.RED
//                    );
//                }else{
//                    drawImage.drawLine(
//                            new DrawImage.Point((int)points[i].x, (int)points[i].y),
//                            new DrawImage.Point((int)points[i+1].x, (int)points[i+1].y),
//                            2, Color.RED
//                    );
//                }
//            }
//        }
//        ImageMat.fromCVMat(drawImage.toMat()).imShow();
    }
}
