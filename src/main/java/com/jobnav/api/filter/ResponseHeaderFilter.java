package com.jobnav.api.filter;

import brave.Tracer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter
@DependsOn({"tracer"})
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResponseHeaderFilter implements Filter {

    Tracer tracer;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("tracer-id", tracer.currentSpan().context().traceIdString());
        chain.doFilter(request, response);
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        // default implementation ignored
    }

    @Override
    public void destroy() {
        // default implementation ignored
    }

}
