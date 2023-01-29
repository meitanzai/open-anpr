package com.visual.open.anpr.server.controller.server.impl;

import com.visual.open.anpr.server.controller.base.BaseController;
import com.visual.open.anpr.server.controller.server.api.PlateControllerApi;
import com.visual.open.anpr.server.domain.common.ResponseInfo;
import com.visual.open.anpr.server.domain.request.PlateInfoReqVo;
import com.visual.open.anpr.server.domain.response.PlateInfoRepVo;
import com.visual.open.anpr.server.service.api.PlateService;
import com.visual.open.anpr.server.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlateControllerImpl extends BaseController implements PlateControllerApi {

    @Autowired
    private PlateService plateService;

    @Override
    public ResponseInfo<List<PlateInfoRepVo>> recognition(PlateInfoReqVo plateInfoReq) {
        try {
            return ResponseBuilder.success(plateService.recognition(plateInfoReq));
        }catch (Exception e){
            logger.error("plate recognition exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

}
