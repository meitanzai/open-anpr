package com.visual.open.anpr.core.base;

import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;

import java.util.List;
import java.util.Map;

public interface PlateDetection {


    /**
     *获取人脸信息
     * @param image     图像信息
     * @param scoreTh   人脸人数阈值
     * @param iouTh     人脸iou阈值
     * @param params    参数信息
     * @return
     */
    List<PlateInfo> inference(ImageMat image, float scoreTh, float iouTh, Map<String, Object> params);


}
