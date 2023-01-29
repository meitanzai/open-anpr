package com.visual.open.anpr.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PlateLocation implements Serializable {

    /**左上角坐标值**/
    @ApiModelProperty(value="左上角坐标值", position = 1, required = true)
    private LocationPoint leftTop;
    /**右上角坐标**/
    @ApiModelProperty(value="右上角坐标", position = 2, required = true)
    private LocationPoint rightTop;
    /**右下角坐标**/
    @ApiModelProperty(value="右下角坐标", position = 3, required = true)
    private LocationPoint rightBottom;
    /**左下角坐标**/
    @ApiModelProperty(value="左下角坐标", position = 4, required = true)
    private LocationPoint leftBottom;

    public LocationPoint getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(LocationPoint leftTop) {
        this.leftTop = leftTop;
    }

    public LocationPoint getRightTop() {
        return rightTop;
    }

    public void setRightTop(LocationPoint rightTop) {
        this.rightTop = rightTop;
    }

    public LocationPoint getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(LocationPoint rightBottom) {
        this.rightBottom = rightBottom;
    }

    public LocationPoint getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(LocationPoint leftBottom) {
        this.leftBottom = leftBottom;
    }


    /**
     * x的最小坐标
     * @return
     */
    public int minX(){
        return Math.min(Math.min(Math.min(leftTop.getX(), rightTop.getX()), rightBottom.getX()), leftBottom.getX());
    }

    /**
     * y的最小坐标
     * @return
     */
    public int minY(){
        return Math.min(Math.min(Math.min(leftTop.getY(), rightTop.getY()), rightBottom.getY()), leftBottom.getY());
    }

    /**
     * x的最大坐标
     * @return
     */
    public int maxX(){
        return Math.max(Math.max(Math.max(leftTop.getX(), rightTop.getX()), rightBottom.getX()), leftBottom.getX());
    }

    /**
     * y的最大坐标
     * @return
     */
    public int maxY(){
        return Math.max(Math.max(Math.max(leftTop.getY(), rightTop.getY()), rightBottom.getY()), leftBottom.getY());
    }
}
