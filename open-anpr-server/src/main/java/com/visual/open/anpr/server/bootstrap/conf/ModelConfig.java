package com.visual.open.anpr.server.bootstrap.conf;

import com.visual.open.anpr.core.base.PlateDetection;
import com.visual.open.anpr.core.base.PlateRecognition;
import com.visual.open.anpr.core.extract.PlateExtractor;
import com.visual.open.anpr.core.extract.PlateExtractorImpl;
import com.visual.open.anpr.core.models.TorchPlateDetection;
import com.visual.open.anpr.core.models.TorchPlateRecognition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("visualModelConfig")
public class ModelConfig {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${visual.model.plateDetection.name:TorchPlateDetection}")
    private String plateDetectionName;
    @Value("${visual.model.plateDetection.modelPath}")
    private String[] plateDetectionModel;
    @Value("${visual.model.plateDetection.thread:4}")
    private Integer plateDetectionThread;

    @Value("${visual.model.plateRecognition.name:TorchPlateRecognition}")
    private String plateRecognitionName;
    @Value("${visual.model.plateRecognition.modelPath}")
    private String[] plateRecognitionNameModel;
    @Value("${visual.model.plateRecognition.thread:4}")
    private Integer plateRecognitionNameThread;

    /**
     * 获取车牌检查模型
     * @return
     */
    @Bean(name = "visualPlateDetection")
    public PlateDetection getPlateDetection(){
        if(plateDetectionName.equalsIgnoreCase("TorchPlateDetection")){
            return new TorchPlateDetection(getModelPath(plateDetectionName, plateDetectionModel)[0], plateDetectionThread);
        }else{
            return new TorchPlateDetection(getModelPath(plateDetectionName, plateDetectionModel)[0], plateDetectionThread);
        }
    }

    /**
     * 人脸特征提取服务
     * @return
     */
    @Bean(name = "visualPlateRecognition")
    public PlateRecognition getPlateRecognition(){
        if(plateRecognitionName.equalsIgnoreCase("TorchPlateRecognition")){
            return new TorchPlateRecognition(getModelPath(plateRecognitionName, plateRecognitionNameModel)[0], plateRecognitionNameThread);
        }else{
            return new TorchPlateRecognition(getModelPath(plateRecognitionName, plateRecognitionNameModel)[0], plateRecognitionNameThread);
        }
    }


    /**
     * 构建特征提取器
     * @param plateDetection     车牌检测模型
     * @param plateRecognition   车牌识别模型
     */
    @Bean(name = "visualPlateExtractor")
    public PlateExtractor getPlateExtractor(
            @Qualifier("visualPlateDetection")PlateDetection plateDetection,
            @Qualifier("visualPlateRecognition")PlateRecognition plateRecognition
    ){
            return new PlateExtractorImpl(plateDetection, plateRecognition);
    }

    /**
     * 获取模型路径
     * @param modelName 模型名称
     * @return
     */
    private String[] getModelPath(String modelName, String modelPath[]){

        String basePath = "open-anpr-core/src/main/resources/";
        if("docker".equalsIgnoreCase(profile)){
            basePath = "/app/open-anpr/";
        }

        if((null == modelPath || modelPath.length != 1) && "TorchPlateDetection".equalsIgnoreCase(modelName)){
            return new String[]{basePath + "models/plate_detect.onnx"};
        }

        if((null == modelPath || modelPath.length != 1) && "TorchPlateRecognition".equalsIgnoreCase(modelName)){
            return new String[]{basePath + "models/plate_rec_color.onnx"};
        }

        return modelPath;
    }
}
