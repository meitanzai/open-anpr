package com.visual.open.anpr.server.controller.server.api;

import com.visual.open.anpr.server.domain.common.ResponseInfo;
import com.visual.open.anpr.server.domain.request.PlateInfoReqVo;
import com.visual.open.anpr.server.domain.response.PlateInfoRepVo;

import java.util.List;


public interface PlateControllerApi {

    /**
     * 识别车牌信息
     * @param plateInfoReq
     * @return
     */
    public ResponseInfo<List<PlateInfoRepVo>> recognition(PlateInfoReqVo plateInfoReq);

}
