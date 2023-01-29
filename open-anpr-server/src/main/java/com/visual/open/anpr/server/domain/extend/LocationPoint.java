package com.visual.open.anpr.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class LocationPoint implements Serializable {

    /**坐标X的值**/
    @ApiModelProperty(value="坐标X的值", position = 1, required = true)
    private int x;
    /**坐标Y的值**/
    @ApiModelProperty(value="坐标Y的值", position = 2, required = true)
    private int y;

    public LocationPoint(){}

    public LocationPoint(float x, float y) {
        this.x = (int)x;
        this.y = (int)y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
