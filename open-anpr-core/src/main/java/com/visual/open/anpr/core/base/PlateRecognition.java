package com.visual.open.anpr.core.base;

import java.util.Map;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo.ParseInfo;

public interface PlateRecognition {

    ParseInfo inference(ImageMat image, boolean single, Map<String, Object> params);
}
