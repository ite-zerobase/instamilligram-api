package com.zerobase.instamilligramapi.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String status;
    private String message;
    private String code;
    private String remark;

    public ErrorResponse(ZbException e) {
        this.status = e.getErrorCode().getStatus().toString();
        this.message = e.getErrorCode().getMessage();
        this.code = e.getErrorCode().getCode();
        this.remark = e.getRemark();
    }
    public static ResponseEntity<ErrorResponse> toResponseEntity(ZbException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e),
                e.getErrorCode().getStatus()
        );
    }
}