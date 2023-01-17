package com.visual.open.anpr.core.extract;

import java.util.Map;
import com.visual.open.anpr.core.domain.ExtParam;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateImage;

public interface PlateExtractor {

    /**
     * 人脸特征提取
     * @param image
     * @param extParam
     * @param params
     * @return
     */
    public PlateImage extract(ImageMat image, ExtParam extParam, Map<String, Object> params);

}
