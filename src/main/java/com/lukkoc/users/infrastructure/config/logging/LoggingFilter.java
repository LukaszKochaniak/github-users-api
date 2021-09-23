package com.lukkoc.users.infrastructure.config.logging;

import com.lukkoc.users.infrastructure.config.logging.cachedrequest.CachedBodyHttpServletRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class LoggingFilter extends OncePerRequestFilter {
    private static final Logger requestLogger = org.slf4j.LoggerFactory.getLogger("RestLogger");
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        requestLog(cachedBodyHttpServletRequest);
        long start = System.currentTimeMillis();

        filterChain.doFilter(cachedBodyHttpServletRequest, responseWrapper);

        logResponse(start, cachedBodyHttpServletRequest, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }

    private void requestLog(CachedBodyHttpServletRequest request) throws JsonProcessingException {
        requestLogger.info(MessageLog.builder()
                .type(MessageType.REQUEST.name())
                .url(getRequestUrl(request))
                .method(request.getMethod())
                .headers(LogUtil.getHeadersFromRequest(request))
                .data(objectMapper.writeValueAsString(new String(request.getCachedBody(), StandardCharsets.UTF_8))).build().toString());
    }

    private void logResponse(long start, CachedBodyHttpServletRequest request, ContentCachingResponseWrapper responseWrapper) throws JsonProcessingException {
        requestLogger.info(
                MessageLog.builder()
                        .type(MessageType.RESPONSE.name())
                        .processTime(System.currentTimeMillis() - start)
                        .url(getRequestUrl(request))
                        .method(request.getMethod())
                        .status(responseWrapper.getStatus())
                        .headers(LogUtil.getHeadersFromResponse(responseWrapper))
                        .data(objectMapper.writeValueAsString(new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8))).build().toString());
    }

    private String getRequestUrl(CachedBodyHttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
    }
}
