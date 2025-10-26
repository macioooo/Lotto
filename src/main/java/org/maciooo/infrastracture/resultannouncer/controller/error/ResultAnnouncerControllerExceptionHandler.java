package org.maciooo.infrastracture.resultannouncer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.maciooo.domain.resultchecker.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ResultAnnouncerControllerExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ResultAnnouncerExceptionResponse> handlePlayerNotFoundException(PlayerNotFoundException exception) {
        final var message = exception.getMessage();
        final var response = new ResultAnnouncerExceptionResponse(message, HttpStatus.NOT_FOUND);
        log.error(message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
