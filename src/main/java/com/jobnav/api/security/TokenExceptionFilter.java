package com.jobnav.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobnav.api.exception.dto.ErrorCodeEnum;
import com.jobnav.api.exception.dto.ErrorResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenExceptionFilter extends OncePerRequestFilter {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (final Exception ex) {
            log.error("Some thing went wrong while authentication: ", ex);
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
}
