package com.visual.open.anpr.core.extract;

import com.visual.open.anpr.core.domain.ExtParam;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateImage;
import com.visual.open.anpr.core.domain.PlateInfo;
import com.visual.open.anpr.core.models.TorchPlateDetection;
import com.visual.open.anpr.core.models.TorchPlateRecognition;
import com.visual.open.anpr.core.utils.CropUtil;
import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateExtractorImpl implements PlateExtractor {

    private TorchPlateDetection torchPlateDetection;
    private TorchPlateRecognition torchPlateRecognition;

    public PlateExtractorImpl(TorchPlateDetection torchPlateDetection, TorchPlateRecognition torchPlateRecognition) {
        this.torchPlateDetection = torchPlateDetection;
        this.torchPlateRecognition = torchPlateRecognition;
    }

    @Override
    public PlateImage extract(ImageMat image, ExtParam extParam, Map<String, Object> params) {
        List<PlateInfo> plateInfos = torchPlateDetection.inference(image, extParam.getScoreTh(),extParam.getIouTh(), new HashMap<>());
        //取人脸topK
        int topK = (extParam.getTopK()  > 0) ? extParam.getTopK() : 5;
        if(plateInfos.size() > topK){
            plateInfos = plateInfos.subList(0, topK);
        }
        //解析车牌信息
        for(PlateInfo plateInfo : plateInfos){
            Mat crop = CropUtil.crop(image.toCvMat(), plateInfo.box);
            plateInfo.parseInfo = torchPlateRecognition.inference(ImageMat.fromCVMat(crop), plateInfo.single, new HashMap<>());
        }
        //返回数据
        return PlateImage.build(image.toBase64AndNoReleaseMat(), plateInfos);
    }
}
