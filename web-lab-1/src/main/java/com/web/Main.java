package com.web;

import com.exceptions.JsonException;
import com.exceptions.ValidationException;
import com.fastcgi.FCGIInterface;

import java.io.IOException;

public class Main {
    private static final Request requestClient = new Request();
    private static final Response responseServer = new Response();

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();
        System.out.println("server start!");
        while (fcgi.FCGIaccept() >= 0) {
            System.out.println("server get request");
            long startTime = System.nanoTime();
            String clientResponse = null;

            try {
                if (FCGIInterface.request != null && FCGIInterface.request.inStream != null) {
                    String body = readRequestBody();
                    Request.RequestDataPoint requestData = requestClient.parseAndValidate(body);
                    Point point = new Point(requestData.x(), requestData.y(), requestData.r(), startTime);
                    clientResponse = responseServer.buildSuccessResponse(point);
                }
            } catch (ValidationException | JsonException e) {
                clientResponse = responseServer.buildErrorResponse(400, e.getMessage());
            } catch (IOException e) {
                clientResponse = responseServer.buildErrorResponse(500, "Failed to read request body");
            } catch (Exception e) {
                clientResponse = responseServer.buildErrorResponse(500, "Internal server error");
            } finally {
                if (clientResponse != null) {
                    System.out.print(clientResponse);
                }
            }

        }
    }

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        byte[] buffer = new byte[FCGIInterface.request.inStream.available()];
        int bytesRead = FCGIInterface.request.inStream.read(buffer);
        return new String(buffer, 0, bytesRead, java.nio.charset.StandardCharsets.UTF_8);
    }
}