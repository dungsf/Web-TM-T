package com.rikkeisoft.canifashop.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponseEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static BaseResponseEntity error(String message) {
        return BaseResponseEntity.builder()
                .success(false)
                .message(message).build();
    }

    public static BaseResponseEntity error(String message, Integer errorCode) {
        return BaseResponseEntity.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message).build();
    }

    public static BaseResponseEntity error(String message, Integer errorCode, Object data) {
        return BaseResponseEntity.builder()
                .success(false)
                .errorCode(errorCode)
                .data(data)
                .message(message).build();
    }

    public static BaseResponseEntity success(String message) {
        return BaseResponseEntity.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static BaseResponseEntity success(Object data) {
        return BaseResponseEntity.builder()
                .success(true)
                .data(data)
                .build();
    }

    public static BaseResponseEntity success(Object data, String message) {
        return BaseResponseEntity.builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }
}
