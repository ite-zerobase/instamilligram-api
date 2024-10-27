package com.zerobase.instamilligramapi.global.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("")
    public ResponseEntity<?> error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("error") instanceof ZbException zbException) {
            return ErrorResponse.toResponseEntity(zbException);
        }
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .status(String.valueOf(response.getStatus()))
                .message(HttpStatus.valueOf(response.getStatus()).name())
                .path((String) request.getAttribute("requestPath"))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(response.getStatus()));
    }

}
