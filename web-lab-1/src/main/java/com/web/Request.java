package com.web;

import com.exceptions.ValidationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Request {
    private static final double X_MIN = -2.0;
    private static final double X_MAX = 2.0;
    private static final double Y_MIN = -3.0;
    private static final double Y_MAX = 5.0;
    private static final double R_MIN = 0.0;
    private static final double R_MAX = 5.0;

    private static final Gson gson = new Gson();

    public RequestDataPoint parseAndValidate(String jsonString) {
        JsonObject json = validateJsonFormat(jsonString);
        double x = json.get("x").getAsDouble();
        double y = json.get("y").getAsDouble();
        double r = json.get("r").getAsDouble();

        validateCoordinate(x, y, r);

        return new RequestDataPoint(x, y, r);
    }

    private JsonObject validateJsonFormat(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            throw new ValidationException("Request body cannot be null or empty");
        }

        try {
            return gson.fromJson(jsonString, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new ValidationException("Invalid JSON format: " + e.getMessage());
        }
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
