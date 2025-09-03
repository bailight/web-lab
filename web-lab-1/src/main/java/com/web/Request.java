package com.web;

import com.exceptions.ValidationException;
import org.json.JSONObject;


public class Request {
    private static final double X_MIN = -2.0;
    private static final double X_MAX = 2.0;
    private static final double Y_MIN = -3.0;
    private static final double Y_MAX = 5.0;
    private static final double R_MIN = 0.0;
    private static final double R_MAX = 5.0;

    public RequestDataPoint parseAndValidate(String json) {
        JSONObject jsonRequest = new JSONObject(json);

        double x = jsonRequest.getDouble("x");
        double y = jsonRequest.getDouble("y");
        double r = jsonRequest.getDouble("r");

        validateCoordinate(x, y, r);

        return new RequestDataPoint(x, y, r);
    }

    private void validateCoordinate(double x, double y, double r) {
        if (x < X_MIN || x > X_MAX) {
            throw new ValidationException("X must be between " + X_MIN + " and " + X_MAX);
        }
        if (y < Y_MIN || y > Y_MAX) {
            throw new ValidationException("Y must be between " + Y_MIN + " and " + Y_MAX);
        }
        if (r < R_MIN || r > R_MAX) {
            throw new ValidationException("R must be between " + R_MIN + " and " + R_MAX);
        }
    }

    public record RequestDataPoint(double x, double y, double r) {
    }

}
