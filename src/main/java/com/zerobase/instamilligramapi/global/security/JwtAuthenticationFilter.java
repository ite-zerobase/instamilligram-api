package com.zerobase.instamilligramapi.global.security;

import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 토큰을 추출
        String token = jwtUtil.extractTokenFromHeader(request);

        try {
            if (token == null) {
                request.setAttribute("error", ZbException.from(ErrorCode.TOKEN_NULL));
            }
            else if (jwtUtil.validateToken(token)) {
                // 유효성 검증 이후 토큰을 context에 저장
                Authentication authentication = jwtUtil.getAuthenticationFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ZbException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            request.setAttribute("error", e);
            request.setAttribute("requestPath", request.getRequestURI());
        }

        chain.doFilter(request, response);
    }
}