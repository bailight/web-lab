package com.web;

import com.exceptions.JsonException;
import com.exceptions.ValidationException;
import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final Request requestClient = new Request();
    private static final Response responseServer = new Response();

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();

        while (fcgi.FCGIaccept() >= 0) {
            long startTime = System.nanoTime();

            try{
                if (fcgi.request != null && fcgi.request.inStream != null){
                    String body = readRequestBody();
                    Request.RequestDataPoint dataPoint = requestClient.parseAndValidate(body);
                    Point point = new Point(dataPoint.x(), dataPoint.y(), dataPoint.r(), startTime);
                    responseServer.buildSuccessResponse(point);
                }else{
                    responseServer.buildErrorResponse(400, "Invalid FCGI request.");
                }
            } catch (JsonException e){
                responseServer.buildErrorResponse(400, "Problem with json");
            } catch (ValidationException e){
                responseServer.buildErrorResponse(400, "Invalid input data");
            } catch (Exception e){
                responseServer.buildErrorResponse(400, e.getMessage());
            }
        }
    }

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        int contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);
        var requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();
        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }
}