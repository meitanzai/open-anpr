package com.visual.open.anpr.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PlateLocation implements Serializable {
    /**左上角x坐标**/
    @ApiModelProperty(value="左上角x坐标", position = 1, required = true)
    private int x;
    /**左上角y坐标**/
    @ApiModelProperty(value="左上角y坐标", position = 2, required = true)
    private int y;
    /**宽度**/
    @ApiModelProperty(value="车牌宽度", position = 3, required = true)
    private int w;
    /**高度**/
    @ApiModelProperty(value="车牌高度", position = 4, required = true)
    private int h;

    /**
     * 构建坐标
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public static PlateLocation build(int x, int y, int w, int h){
        return new PlateLocation().setX(x).setY(y).setW(w).setH(h);
    }

    public static PlateLocation build(float x, float y, float w, float h){
        return new PlateLocation().setX((int) x).setY((int) y).setW((int) w).setH((int) h);
    }

    public int getX() {
        return x;
    }

    public PlateLocation setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public PlateLocation setY(int y) {
        this.y = y;
        return this;
    }

    public int getW() {
        return w;
    }

    public PlateLocation setW(int w) {
        this.w = w;
        return this;
    }

    public int getH() {
        return h;
    }

    public PlateLocation setH(int h) {
        this.h = h;
        return this;
    }
}
