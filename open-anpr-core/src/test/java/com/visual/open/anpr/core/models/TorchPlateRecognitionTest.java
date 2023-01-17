package com.visual.open.anpr.core.models;

import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;
import com.visual.open.anpr.core.utils.CropUtil;
import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.List;

public class TorchPlateRecognitionTest {

    public static void main(String[] args) {
        TorchPlateDetection torchPlateDetection = new TorchPlateDetection("open-anpr-core/src/main/resources/models/plate_detect.onnx", 1);
        TorchPlateRecognition torchPlateRecognition = new TorchPlateRecognition("open-anpr-core/src/main/resources/models/plate_rec_color.onnx", 1);

//        String imagePath = "/Users/diven/workspace/pycharm/github/Chinese_license_plate_detection_recognition/imgs3/double_yellow.jpg";
        String imagePath = "/Users/diven/workspace/pycharm/github/Chinese_license_plate_detection_recognition/imgs2/image007.jpg";
        ImageMat imageMat = ImageMat.fromImage(imagePath);
        List<PlateInfo> plateInfos = torchPlateDetection.inference(imageMat, 0.3f,0.5f, new HashMap<>());
        System.out.println(plateInfos);
        for(PlateInfo plateInfo : plateInfos){
            Mat crop = CropUtil.crop(imageMat.toCvMat(), plateInfo.box);
//            ImageMat.fromCVMat(crop).imShow();
            PlateInfo.ParseInfo parseInfo = torchPlateRecognition.inference(ImageMat.fromCVMat(crop), plateInfo.single, new HashMap<>());
            System.out.println(parseInfo);
        }
    }
}
