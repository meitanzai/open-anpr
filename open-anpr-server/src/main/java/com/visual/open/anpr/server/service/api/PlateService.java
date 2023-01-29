package com.visual.open.anpr.server.service.api;

import com.visual.open.anpr.server.domain.request.PlateInfoReqVo;
import com.visual.open.anpr.server.domain.response.PlateInfoRepVo;

import java.util.List;

public interface PlateService {

    /**
     * 识别车牌信息
     * @param plateInfoReq
     * @return
     */
    public List<PlateInfoRepVo> recognition(PlateInfoReqVo plateInfoReq);

}
