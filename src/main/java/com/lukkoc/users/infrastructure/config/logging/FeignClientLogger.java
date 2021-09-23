package com.lukkoc.users.infrastructure.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.slf4j.Slf4jLogger;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignClientLogger extends Slf4jLogger {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger("ClientRestLogger");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        logger.info(objectMapper.writeValueAsString(MessageLog.builder()
                .type(MessageType.REQUEST.name())
                .headers(LogUtil.getFeignHeaders(request.headers()))
                .url(request.requestTemplate().url())
                .method(request.httpMethod().name())
                .data(request.body() != null ? new String(request.body(), request.charset()) : null).build()));
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        byte[] bodyData = response.body() != null ? Util.toByteArray(response.body().asInputStream()) : null;

        logger.info(objectMapper.writeValueAsString(
                MessageLog.builder()
                        .type(MessageType.RESPONSE.name())
                        .headers(LogUtil.getFeignHeaders(response.headers()))
                        .url(response.request().requestTemplate().url())
                        .method(response.request().httpMethod().name())
                        .status(response.status())
                        .data(bodyData != null ? new String(bodyData, StandardCharsets.UTF_8) : null).build()));

        return response.toBuilder().body(bodyData).build();
    }
}