package web.servlet;

public class AreaCheckServlet {
    private static boolean check_point(double x, double y, double r){
        if (x<=0 && y<=0){
            return y<r/2 && x<r; //第三象限 长方形
        } else if (x<=0 && y>=0) {
            return -r/2<x && y<r/2; //第二象限 三角形
        } else if (x >= 0 && y<=0) {
            return x * x + y * y < r * r; //第四象限 1/4圆形
        }else{
            return false;
        }
    }
}
