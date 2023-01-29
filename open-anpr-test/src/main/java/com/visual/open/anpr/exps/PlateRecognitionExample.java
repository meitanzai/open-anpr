package com.visual.open.anpr.exps;

import com.visual.open.anpr.PlateRecognition;
import com.visual.open.anpr.model.*;
import com.visual.open.anpr.utils.Base64Util;
import com.visual.open.anpr.utils.DrawImage;
import org.opencv.highgui.HighGui;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class PlateRecognitionExample {

    static{ nu.pattern.OpenCV.loadShared(); }
    //本地开发模式
    public static String serverHost = "http://127.0.0.1:8080";
    //docker部署模式
    //public static String serverHost = "http://127.0.0.1:56790";
    //远程测试服务
    //public static String serverHost = "http://open-anpr.diven.nat300.top";

    public static PlateRecognition plateRecognition = PlateRecognition.build(serverHost);

    /**搜索*/
    public static void recognition() {
        String searchPath = "open-anpr-test/src/main/resources/image";
        File [] files = Objects.requireNonNull(new File(searchPath).listFiles());
        for(int i=0; i< files.length-1; i++){
            File imageA = files[i];
            String imageBase64 = Base64Util.encode(imageA.getAbsolutePath());
            Response<List<RecognitionRep>> response = plateRecognition.detection().recognition(
                Recognition.build().setImage(imageBase64).setLimit(5)
            );

            DrawImage drawImage = DrawImage.build(imageA.getAbsolutePath());
            if(response.ok()){
                for(RecognitionRep recognitionRep : response.getData()){
                    PlateLocation location = recognitionRep.getLocation();
                    drawImage.drawRect(
                            new DrawImage.Rect(location.getX(), location.getY(), location.getW(), location.getH()), 2, Color.RED);


                    RecognitionInfo recognition = recognitionRep.getRecognition();
                    int fonSize = Float.valueOf(1.4f * location.getW() / recognition.getPlateNo().length()).intValue();
                    drawImage.drawText(recognition.getPlateNo(),
                            new DrawImage.Point(location.getX(), location.getY()-(int)(fonSize*2.2)), fonSize, Color.RED);
                    drawImage.drawText((recognition.getLayout() == PlateLayout.SINGLE  ? "单排" : "双排") + ":" + recognition.getPlateColor().getName(),
                            new DrawImage.Point(location.getX(), location.getY()-(int)(fonSize*1.2)), fonSize, Color.RED);
                }
            }
            HighGui.imshow("image", drawImage.toMat());
            HighGui.waitKey();
        }
        //退出程序
        System.exit(1);
    }

    /**main**/
    public static void main(String[] args) {
        recognition();
    }

}
