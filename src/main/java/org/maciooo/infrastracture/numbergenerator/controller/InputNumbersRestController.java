package org.maciooo.infrastracture.numbergenerator.controller;

import lombok.AllArgsConstructor;
import org.maciooo.domain.numberreceiver.NumberReceiverFacade;
import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
import org.maciooo.domain.numberreceiver.dto.TicketDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
public class InputNumbersRestController {
    private final NumberReceiverFacade numberReceiverFacade;
    @PostMapping("/inputNumbers")
    public ResponseEntity<InputNumberResultDto> inputNumbers(@RequestBody TicketDto dto) {
        Set<Integer> mapDto = new HashSet<>(dto.numbersFromUser());
        final var result = numberReceiverFacade.inputNumbers(mapDto);
        return ResponseEntity.ok(result);
    }

}
