package com.back.util;

public class ValidData {
    private static final double X_MIN = -5.0;
    private static final double X_MAX = 3.0;
    private static final double Y_MIN = -5.0;
    private static final double Y_MAX = 5.0;
    private static final double R_MIN = 0.0;
    private static final double R_MAX = 3.0;

    public static void validateCoordinate(double x, double y, double r){
        if(x < X_MIN || x > X_MAX){
            throw new IllegalArgumentException("X must be between " + X_MIN + " and " + X_MAX);
        }
        if(y < Y_MIN || y > Y_MAX){
            throw new IllegalArgumentException("Y must be between " + Y_MIN + " and " + Y_MAX);
        }
        if(r < R_MIN || r > R_MAX){
            throw new IllegalArgumentException("R must be between " + R_MIN + " and " + R_MAX);
        }
    }
}
