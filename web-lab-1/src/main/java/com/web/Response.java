package com.web;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class Response {
    private static final String RESPONSE_TEMPLATE = "Content-Type: application/json\nContent-Length: %d\nStatus: %d\n\n%s";

    public void buildSuccessResponse(Point point){

        String jsonResponse = new JSONObject()
                .put("x", point.x)
                .put("y", point.y)
                .put("r", point.r)
                .put("result", point.check)
                .put("clickTime", point.clickTime)
                .put("executionTime", point.executionTime + "ns")
                .toString();

        sendJson(200, jsonResponse);

    }

    public void buildErrorResponse(int statusCode, String message) {
        String jsonResponse = new JSONObject()
                .put("error", message)
                .toString();
        sendJson(statusCode, jsonResponse);
    }

    private static void sendJson(int statusCode, String json) {
        System.out.printf(RESPONSE_TEMPLATE, json.getBytes(StandardCharsets.UTF_8).length, statusCode, json);
    }

}
