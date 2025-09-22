package web.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Point {
    public double x;
    public double y;
    public double r;
    public boolean check;
    public String clickTime;
    public String executionTime;

    public Point(double x, double y, double r, boolean check, long startTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.check = false;
        this.clickTime =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.executionTime = String.valueOf(System.nanoTime() - startTime);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getClickTime() {
        return clickTime;
    }

    public void setClickTime(String clickTime) {
        this.clickTime = clickTime;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString(){
        return "Point={" +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "r=" + r + ", " +
                "check=" + check + ", " +
                "currentTime:"+ clickTime + ", " +
                "executionTime:" + executionTime + "}";
    }
}
