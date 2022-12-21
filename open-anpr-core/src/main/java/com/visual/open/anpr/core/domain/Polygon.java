package com.visual.open.anpr.core.domain;

import org.opencv.core.Point;

import java.util.List;

public class Polygon {
    private List<Point> points;

    public Polygon(List<Point> points){
        this.points = points;
    }

    public double getArea(){
        int i, j;
        double area = 0;
        for (i = 0; i < this.points.size(); i++)
        {
            j = (i + 1) % this.points.size();
            area += this.points.get(i).x * this.points.get(j).y;
            area -= this.points.get(i).y * this.points.get(j).x;
        }
        area /= 2;
        return Math.abs(area);
    }

    public double getLength(){
        double result = 0;
        for (int i = 0; i < this.points.size(); i++){
            Point u = points.get(i);
            Point v = (i+1==this.points.size()) ? points.get(0): points.get(i+1);
            double m = u.x - v.x;
            double n = u.y - v.y;
            result += Math.sqrt(m * m + n * n);
        }
        return result;
    }

}
