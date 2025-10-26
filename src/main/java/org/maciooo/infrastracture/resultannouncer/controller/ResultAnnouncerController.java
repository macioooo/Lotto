package org.maciooo.infrastracture.resultannouncer.controller;


import lombok.AllArgsConstructor;
import org.maciooo.domain.resultannouncer.ResultAnnouncerFacade;
import org.maciooo.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
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
        return ResponseEntity.ok(result);
    }
}
