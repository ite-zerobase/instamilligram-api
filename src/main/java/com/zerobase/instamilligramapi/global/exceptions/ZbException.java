package com.zerobase.instamilligramapi.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
@ToString
public class ZbException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String remark;

    public static ZbException from(ErrorCode errorCode, String remark) {
        return new ZbException(errorCode, remark);
    }
    public static ZbException from(ErrorCode errorCode) {
        return new ZbException(errorCode, null);
    }
    public static Supplier<ZbException> supplier(ErrorCode errorCode) {
        return () -> ZbException.from(errorCode);
    }
    public static Supplier<ZbException> supplier(ErrorCode errorCode, String remark) {
        return () -> ZbException.from(errorCode, remark);
    }
}