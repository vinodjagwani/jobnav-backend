package com.jobnav.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.exception.dto.ErrorResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public final class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException ex) {
        log.error("Unauthorized error: ", ex);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        final ErrorResponse.ErrorInfo errorInfo = ErrorResponse.ErrorInfo.builder()
                .reason(ErrorCodeEnum.UNAUTHORIZED.name())
                .domain("Authorization")
                .message("Full authentication is required to access this resource")
                .build();
        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorInfo));
    }
}
