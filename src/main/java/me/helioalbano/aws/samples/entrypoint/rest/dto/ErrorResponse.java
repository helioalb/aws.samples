package me.helioalbano.aws.samples.entrypoint.rest.dto;

import java.time.ZonedDateTime;

public record ErrorResponse(ZonedDateTime timestamp, int code, String message) {
}
