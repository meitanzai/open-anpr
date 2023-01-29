package com.visual.open.anpr.server.controller.server.restful;

import com.visual.open.anpr.server.controller.server.impl.PlateControllerImpl;
import com.visual.open.anpr.server.domain.common.ResponseInfo;
import com.visual.open.anpr.server.domain.request.PlateInfoReqVo;
import com.visual.open.anpr.server.domain.response.PlateInfoRepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags="01、车牌识别服务")
@RestController("visualPlateController")
@RequestMapping("/visual/plate")
public class PlateController extends PlateControllerImpl {

    @ApiOperation(value="1、车牌识别", position = 1)
    @Override
    @ResponseBody
    @RequestMapping(value = "/recognition", method = RequestMethod.POST)
    public ResponseInfo<List<PlateInfoRepVo>> recognition(@RequestBody @Valid PlateInfoReqVo plateInfoReq) {
        return super.recognition(plateInfoReq);
    }
}
