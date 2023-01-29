package com.visual.open.anpr.server.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "PlateInfoReqVo",  description="车牌识别参数")
public class PlateInfoReqVo implements Serializable {

    /**图像Base64编码值**/
    @NotNull(message = "image cannot be empty")
    @ApiModelProperty(value="图像Base64编码值", position = 1,required = true)
    private String image;

    /**搜索条数：默认5**/
    @Min(value = 0, message = "limit must greater than or equal to 0")
    @ApiModelProperty(value="最大搜索条数：默认5", position = 3, required = false)
    private Integer limit = 5;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
