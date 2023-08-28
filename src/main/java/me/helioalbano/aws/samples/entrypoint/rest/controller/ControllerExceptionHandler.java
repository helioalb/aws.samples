package me.helioalbano.aws.samples.entrypoint.rest.controller;

import java.time.ZonedDateTime;
import me.helioalbano.aws.samples.entrypoint.rest.dto.ErrorResponse;
import me.helioalbano.aws.samples.exception.CreateBucketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(CreateBucketException.class)
    public ResponseEntity<ErrorResponse> handleCreateBucketException(CreateBucketException e) {
        log.error(buildErrorLog(e));
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(buildBadGatewayResponse(e.getMessage()));
    }

    private String buildErrorLog(Throwable e) {
        return String.format("[message=%s][cause=%s]", e.getMessage(), e.getCause().getMessage());
    }

    private ErrorResponse buildBadGatewayResponse(String message) {
        return new ErrorResponse(
            ZonedDateTime.now(),
            HttpStatus.BAD_GATEWAY.value(),
            message
        );
    }
}
