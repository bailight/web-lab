package com.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    public double x;
    public double y;
    public double r;
    public boolean check;
    public String clickTime;
    public String executionTime;

    public Response(double x, double y, double r, long startTime){
        this.x = x;
        this.y = y;
        this.r = r;
        this.check = inGraph(x, y, r);
        this.clickTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.executionTime = String.valueOf(System.nanoTime() - startTime);
    }

    private static boolean inGraph(double x, double y, double r){
        if (x<=0 && y>=0){
            return x>=r && y<r/2; //第二象限 长方形
        } else if (x>=0 && y>=0) {
            return y <= (r - x); //第一象限 三角形
        } else if (x >= 0 && y<=0) {
            return x * x + y * y < r * r; //第四象限 1/4圆形
        }
        return false; //第三象限 无色块
    }

    @Override
    public String toString() {
        return "Response: {" +
                "x: " + x + "," +
                "y: " + y + "," +
                "r: " + r + "," +
                "check: " + check + "," +
                "currentTime: "+ clickTime + "," +
                "executionTime: " + executionTime + "}";
    }
}
