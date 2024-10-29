package com.zerobase.instamilligramapi.global.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ZbAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Retrieve the original error if available
        response.setContentType("application/json");
        ErrorResponse errorResponse;
        if (request.getAttribute("error") instanceof ZbException zbException) {
            errorResponse = ErrorResponse.of(zbException);
        }
        else {
            errorResponse = new ErrorResponse(
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    null,
                    null,
                    null,
                    request.getAttribute("requestPath").toString(),
                    LocalDateTime.now()
                    );
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}