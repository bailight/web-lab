package com.back.DTO;

import lombok.Data;


@Data
public class CommonResponseDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> CommonResponseDTO<T> success(T data) {
        CommonResponseDTO<T> response = new CommonResponseDTO<>();
        response.setCode(200);
        response.setMessage("操作成功");
        response.setData(data);
        return response;
    }

    public static <T> CommonResponseDTO<T> fail(Integer code, String message) {
        CommonResponseDTO<T> response = new CommonResponseDTO<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}