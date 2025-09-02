package com.web;

import com.google.gson.*;

public class Response {
    private static final String RESPONSE_TEMPLATE = "Content-Type: application/json\nContent-Length: %d\nStatus: %d\n\n%s";

    public String buildSuccessResponse(Point point){
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("status", "success");

        JsonObject pointJson = new JsonObject();
        pointJson.addProperty("x", point.x);
        pointJson.addProperty("y", point.y);
        pointJson.addProperty("r", point.r);
        pointJson.addProperty("check", point.check);
        pointJson.addProperty("clickTime", point.clickTime);
        pointJson.addProperty("executionTime", point.executionTime);

        responseJson.add("data", pointJson);
        return formatResponse(200, responseJson.toString());
    }

    public String buildErrorResponse(int statusCode, String message) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("status", statusCode);
        responseJson.addProperty("message", message);
        return formatResponse(statusCode, responseJson.toString());
    }

    private String formatResponse(int statusCode, String json) {
        int contentLength = json.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
        return String.format(RESPONSE_TEMPLATE, contentLength, statusCode, json);
    }
}
