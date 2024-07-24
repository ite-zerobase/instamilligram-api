package com.zerobase.instamilligramapi.global.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Business Exception Hanlding
     * 비즈니스 요구사항 예외 처리
     */
    @ExceptionHandler(ZbException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final ZbException e) {
        log.error("[BILLIONAIRE EXCEPTION HANDLING] " + e.toString(), e);
        return ErrorResponse.toResponseEntity(e);
    }
}