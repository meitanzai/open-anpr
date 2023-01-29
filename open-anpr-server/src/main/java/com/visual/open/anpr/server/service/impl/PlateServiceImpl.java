package com.visual.open.anpr.server.service.impl;

import com.visual.open.anpr.core.domain.ExtParam;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateImage;
import com.visual.open.anpr.core.domain.PlateInfo;
import com.visual.open.anpr.core.extract.PlateExtractor;
import com.visual.open.anpr.server.domain.extend.*;
import com.visual.open.anpr.server.domain.request.PlateInfoReqVo;
import com.visual.open.anpr.server.domain.response.PlateInfoRepVo;
import com.visual.open.anpr.server.service.api.PlateService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("plateServiceImpl")
public class PlateServiceImpl implements PlateService {

    @Resource
    private PlateExtractor plateExtractor;


    @Override
    public List<PlateInfoRepVo> recognition(PlateInfoReqVo plateInfoReq) {
        //模型检测车牌信息
        ImageMat imageMat = null;
        PlateImage plateImage = null;
        try {
            int topK = (null == plateInfoReq.getLimit() || plateInfoReq.getLimit()  <= 0) ? 5 : plateInfoReq.getLimit();
            ExtParam extParam = ExtParam.build().setTopK(topK);
            imageMat = ImageMat.fromBase64(plateInfoReq.getImage());
            plateImage = plateExtractor.extract(imageMat, extParam, new HashMap<>());
        }finally {
            if(null != imageMat){
                imageMat.release();
            }
        }
        if(null == plateImage){
            throw new RuntimeException("PlateExtractor extract error");
        }
        //转换模型结果，并输出
        List<PlateInfoRepVo> plates = new ArrayList<>();
        if(null != plateImage.PlateInfos() && !plateImage.PlateInfos().isEmpty()){
            for(PlateInfo plateInfo : plateImage.PlateInfos()){
                //检测信息
                PlateInfoRepVo plate = new PlateInfoRepVo();
                plate.setScore((float)Math.floor(plateInfo.score * 1000000)/10000);
                PlateLocation location = new PlateLocation();
                location.setLeftTop(new LocationPoint(plateInfo.box.leftTop.x, plateInfo.box.leftTop.y));
                location.setRightTop(new LocationPoint(plateInfo.box.rightTop.x, plateInfo.box.rightTop.y));
                location.setRightBottom(new LocationPoint(plateInfo.box.rightBottom.x, plateInfo.box.rightBottom.y));
                location.setLeftBottom(new LocationPoint(plateInfo.box.leftBottom.x, plateInfo.box.leftBottom.y));
                plate.setLocation(location);

                //识别信息
                RecognitionInfo recognition = new RecognitionInfo();
                recognition.setLayout(plateInfo.single ? PlateLayout.SINGLE : PlateLayout.DOUBLE);
                recognition.setPlateNo(plateInfo.parseInfo.plateNo);
                recognition.setPlateColor(PlateColor.valueOfName(plateInfo.parseInfo.plateColor));
                plate.setRecognition(recognition);
                //添加车牌
                plates.add(plate);
            }
        }
        return plates;
    }
}
