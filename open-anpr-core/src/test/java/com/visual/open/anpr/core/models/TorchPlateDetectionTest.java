package com.visual.open.anpr.core.models;

import com.visual.open.anpr.core.domain.DrawImage;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class TorchPlateDetectionTest {
    public static void main(String[] args) {
        TorchPlateDetection torchPlateDetection = new TorchPlateDetection("open-anpr-core/src/main/resources/models/plate_detect.onnx", 1);
        String imagePath = "/Users/diven/workspace/idea/gitee/open-anpr/open-anpr-core/src/test/resources/images/image003.jpg";
//        String imagePath = "/Users/diven/workspace/pycharm/github/Chinese_license_plate_detection_recognition/imgs3/double_yellow.jpg";
        ImageMat imageMat = ImageMat.fromImage(imagePath);
        List<PlateInfo> plateInfos = torchPlateDetection.inference(imageMat, 0.3f,0.5f, new HashMap<>());
        System.out.println(plateInfos);

        DrawImage drawImage = DrawImage.build(imagePath);
        for(PlateInfo plateInfo : plateInfos){
            PlateInfo.Point [] points = plateInfo.box.toArray();
            for(int i =0; i< points.length; i++){
                if(i+1 == points.length){
                    drawImage.drawLine(
                            new DrawImage.Point((int)points[i].x, (int)points[i].y),
                            new DrawImage.Point((int)points[0].x, (int)points[0].y),
                            2, Color.RED
                    );
                }else{
                    drawImage.drawLine(
                            new DrawImage.Point((int)points[i].x, (int)points[i].y),
                            new DrawImage.Point((int)points[i+1].x, (int)points[i+1].y),
                            2, Color.RED
                    );
                }
            }
        }
        ImageMat.fromCVMat(drawImage.toMat()).imShow();
    }

}
