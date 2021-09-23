package com.lukkoc.users.infrastructure.exceptions;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
class ExceptionHandlerAdvice {
    private static final int FEIGN_RETRYABLE_EXCEPTION_STATUS = -1;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ApiError> handleException(Exception ex) {
        return createAndReturnResponseEntity("Internal error on server occurred",
                HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<ApiError> handleFeignException(FeignException ex) {
        return createAndReturnResponseEntity("Error on client-side occurred", determineHttpResponseStatusCode(ex.status()), ex);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    ResponseEntity<ApiError> handleFeignException(FeignException.NotFound ex) {
        return createAndReturnResponseEntity("User with this username is not present on github", HttpStatus.NOT_FOUND, ex);
    }

    private ResponseEntity<ApiError> createAndReturnResponseEntity(String customLogMessage, HttpStatus httpStatus, Exception exception) {
        ApiError apiError = new ApiError(httpStatus, exception.getMessage());
        log.error(customLogMessage + exception.getMessage());
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private HttpStatus determineHttpResponseStatusCode(int feignExceptionStatus) {
        if (feignExceptionStatus == FEIGN_RETRYABLE_EXCEPTION_STATUS) {
            return HttpStatus.valueOf(500);
        } else if (feignExceptionStatus >= 500) {
            return HttpStatus.valueOf(502);
        } else {
            return HttpStatus.valueOf(feignExceptionStatus);
        }
    }
}
