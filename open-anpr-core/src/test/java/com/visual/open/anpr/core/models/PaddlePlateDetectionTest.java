package com.visual.open.anpr.core.models;

import com.visual.open.anpr.core.domain.DrawImage;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PaddlePlateDetectionTest {

    public static void main(String[] args) {
        PaddlePlateDetection detection = new PaddlePlateDetection("open-anpr-core/src/main/resources/models/det.onnx", 1);

        String imagePath = "open-anpr-core/src/test/resources/images/image005.jpg";
        ImageMat imageMat = ImageMat.fromImage(imagePath);
        List<PlateInfo> plateInfos = detection.inference(imageMat, 1,1, new HashMap<>());

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
