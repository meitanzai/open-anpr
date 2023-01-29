package com.visual.open.anpr.server.service.impl;

import com.visual.open.anpr.core.domain.ExtParam;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateImage;
import com.visual.open.anpr.core.extract.PlateExtractor;
import com.visual.open.anpr.server.domain.request.PlateInfoReqVo;
import com.visual.open.anpr.server.domain.response.PlateInfoRepVo;
import com.visual.open.anpr.server.service.api.PlateService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
        System.out.println(plateImage.PlateInfos().size());

        return null;
    }
}
