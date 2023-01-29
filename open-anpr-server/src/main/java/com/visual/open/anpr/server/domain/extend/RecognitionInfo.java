package com.visual.open.anpr.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RecognitionInfo implements Serializable {
    /**车牌布局，单排还是双排**/
    @ApiModelProperty(value="车牌布局，单排还是双排", position = 1, required = true)
    private PlateLayout layout;
    /**车牌文本信息**/
    @ApiModelProperty(value="车牌文本信息", position = 3, required = true)
    private String plateNo;
    /**车牌的颜色信息**/
    @ApiModelProperty(value="车牌的颜色信息", position = 5, required = true)
    private PlateColor plateColor;

    public PlateLayout getLayout() {
        return layout;
    }

    public void setLayout(PlateLayout layout) {
        this.layout = layout;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public PlateColor getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(PlateColor plateColor) {
        this.plateColor = plateColor;
    }
}
