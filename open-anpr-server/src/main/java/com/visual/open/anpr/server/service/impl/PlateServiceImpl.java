package com.visual.open.anpr.server.service.impl;

import com.visual.open.anpr.core.extract.PlateExtractor;
import com.visual.open.anpr.server.service.api.PlateService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("plateServiceImpl")
public class PlateServiceImpl implements PlateService {

    @Resource
    private PlateExtractor plateExtractor;


}
