package com.zerobase.instamilligramapi.global.dto;

import lombok.Data;

@Data
public class BaseResponse {
    private boolean success;

    public static BaseResponse success() {
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        return response;
    }
    public static BaseResponse fail() {
        return new BaseResponse();
    }
}
