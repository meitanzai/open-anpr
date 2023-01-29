package com.visual.open.anpr.server.domain.response;

import com.visual.open.anpr.server.domain.extend.PlateLocation;
import com.visual.open.anpr.server.domain.extend.RecognitionInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PlateInfoRepVo implements Serializable {
    /**车牌置信分数**/
    @ApiModelProperty(value="车牌置信分数:[0,100]", position = 1, required = true)
    private Float plateScore;

    /**车牌位置信息**/
    @ApiModelProperty(value="车牌位置信息", position = 3, required = true)
    private PlateLocation location;

    /**车牌识别信息**/
    @ApiModelProperty(value="车牌识别信息", position = 5, required = true)
    private RecognitionInfo recognition;


    public Float getPlateScore() {
        return plateScore;
    }

    public void setPlateScore(Float plateScore) {
        this.plateScore = plateScore;
    }

    public PlateLocation getLocation() {
        return location;
    }

    public void setLocation(PlateLocation location) {
        this.location = location;
    }

    public RecognitionInfo getRecognition() {
        return recognition;
    }

    public void setRecognition(RecognitionInfo recognition) {
        this.recognition = recognition;
    }
}
