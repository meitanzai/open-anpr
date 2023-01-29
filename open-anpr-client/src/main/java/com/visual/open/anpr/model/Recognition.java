package com.visual.open.anpr.model;

import java.io.Serializable;

public class Recognition<ExtendsVo extends Recognition<ExtendsVo>> implements Serializable {

    /**图像Base64编码值**/
    private String image;

    /**搜索条数：默认5**/
    private Integer limit = 5;

    /**
     * 构建比对对象
     * @return
     */
    public static Recognition build(){
        return new Recognition();
    }


    public String getImage() {
        return image;
    }

    public ExtendsVo setImage(String image) {
        this.image = image;
        return (ExtendsVo) this;
    }

    public Integer getLimit() {
        return limit;
    }

    public ExtendsVo setLimit(Integer limit) {
        this.limit = limit;
        return (ExtendsVo) this;
    }

}
