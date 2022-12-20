package com.visual.open.anpr.core.models;

import ai.onnxruntime.OnnxTensor;
import com.visual.open.anpr.core.base.BaseOnnxInfer;
import com.visual.open.anpr.core.base.PlateDetection;
import com.visual.open.anpr.core.domain.ImageMat;
import com.visual.open.anpr.core.domain.PlateInfo;
import com.visual.open.anpr.core.utils.ReleaseUtil;
import org.opencv.core.Scalar;

import java.util.List;
import java.util.Map;

public class PaddlePlateDetection extends BaseOnnxInfer implements PlateDetection {

    private float std[] = {0.229f, 0.224f, 0.225f};
    private Scalar mean = new Scalar(0.485, 0.456, 0.406);

    public PaddlePlateDetection(String modelPath, int threads) {
        super(modelPath, threads);
    }

    @Override
    public List<PlateInfo> inference(ImageMat image, float scoreTh, float iouTh, Map<String, Object> params) {
        ImageMat imageMat = image.clone();
        OnnxTensor tensor = null;
        try {
            tensor = imageMat
                    .blobFromImageAndDoReleaseMat(1.0/255, mean, true)
                    .to4dFloatOnnxTensorAndNoReleaseMat(std,true);



        }catch (Exception e){

        }finally {
            ReleaseUtil.release(tensor);
        }

        return null;
    }


}
