package org.maciooo.infrastracture.resultannouncer.controller.error;

import org.springframework.http.HttpStatus;

public record ResultAnnouncerExceptionResponse(
        String message,
        HttpStatus status
) {
}
