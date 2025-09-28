package org.maciooo.infrastracture.resultannouncer.controller;


import lombok.AllArgsConstructor;
import org.maciooo.domain.resultannouncer.ResultAnnouncerFacade;
import org.maciooo.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ResultAnnouncerController {
    private final ResultAnnouncerFacade announcerFacade;
    @GetMapping("/results/{ticketId}")
    ResponseEntity<ResultAnnouncerResponseDto> getResult(@PathVariable String ticketId) {
        final var result = announcerFacade.announceResult(ticketId);
        if (result.message().equals("Sorry, we couldn't find the ticket with this ID. Check if it's correct and try again")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
