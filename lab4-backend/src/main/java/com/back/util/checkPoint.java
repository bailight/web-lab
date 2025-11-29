package com.back.util;

public class checkPoint {
    public static boolean isValidX(double x) {
        return x >= -5 && x <= 3;
    }

    public static boolean isValidY(double y) {
        return y >= -5 && y <= 5;
    }

    public static boolean isValidR(double radius) {
        return radius >= -5 && radius <= 3;
    }

    public static boolean inGraph(double x, double y, double r){
        if (x<=0 && y>=0){
            return x*x + y*y < (r/2)*(r/2); //第二象限 1/4小圆形
        } else if (x>=0 && y>=0) {
            return y <= (r - x); //第一象限 三角形
        } else if (x <= 0 && y<=0) {
            return y<-r && x<-r/2; //第三象限 长方形
        }
        return false; //第四象限 无色块
    }
}
