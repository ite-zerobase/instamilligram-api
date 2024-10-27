package com.zerobase.instamilligramapi.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ErrorResponse;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.nio.charset.StandardCharsets;

@Configuration
public class FilterExceptionHandler {
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        ObjectMapper objectMapper = new ObjectMapper();
        return (request, response, accessDeniedException) -> {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code("403")
                    .message("접근 권한이 없습니다.")
                    .build();

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            objectMapper.writeValue(response.getWriter(), errorResponse);
        };
    }
}
