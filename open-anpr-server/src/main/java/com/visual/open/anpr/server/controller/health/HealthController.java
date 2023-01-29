package com.visual.open.anpr.server.controller.health;

import com.visual.open.anpr.server.domain.common.ResponseInfo;
import com.visual.open.anpr.server.utils.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="02、健康检测服务")
@RestController("healthController")
@RequestMapping("/common/health")
public class HealthController {

    @ApiOperation(value="1、健康检测服务")
    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseInfo<String> check(){
        return ResponseBuilder.success("health check is ok");
    }

}
