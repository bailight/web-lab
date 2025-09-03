package com.web;

import com.google.gson.*;

public class Response {
    private static final Gson gson = new GsonBuilder().create();

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
        return formatResponse(200, gson.toJson(responseJson));
    }

    public String buildErrorResponse(int statusCode, String message) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("status", statusCode);
        responseJson.addProperty("message", message);
        return formatResponse(statusCode, gson.toJson(responseJson));
    }

    private String formatResponse(int statusCode, String json) {
        int contentLength = json.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
        String statusMessage = getStatusMessage(statusCode);

        return "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "\r\n" +
                json;
    }

    private String getStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 400 -> "Bad Request";
            case 500 -> "Internal Server Error";
            default -> "Unknown";
        };
    }
}
