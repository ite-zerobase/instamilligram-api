package com.zerobase.instamilligramapi.global.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timestamp;

    public ErrorResponse(ZbException e) {
        this.status = e.getErrorCode().getStatus().toString();
        this.message = e.getErrorCode().getMessage();
        this.code = e.getErrorCode().getCode();
        this.remark = e.getRemark();
    }
    public static ErrorResponse of(ZbException e) {
        return new ErrorResponse(e);
    }
    public static ResponseEntity<ErrorResponse> toResponseEntity(ZbException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e),
                e.getErrorCode().getStatus()
        );
    }
}