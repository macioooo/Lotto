package org.maciooo.domain.resultannouncer;

import lombok.AllArgsConstructor;
import org.maciooo.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import org.maciooo.domain.resultannouncer.dto.ResultResponseDto;
import org.maciooo.domain.resultchecker.ResultCheckerFacade;
import org.maciooo.domain.resultchecker.dto.PlayerDto;

import java.time.Clock;

import static org.maciooo.domain.resultannouncer.ResultAnnouncerMapper.mapFromPlayerDtoToResultResponse;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMapper.mapFromResultResponseToDto;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMessages.*;

@AllArgsConstructor
public class ResultAnnouncerFacade {
    private final ResultAnnouncerRepository repository;
    private final ResultCheckerFacade checkerFacade;
    private final Clock clock;
    private final ResultAnnouncerValidator validator;

    public ResultAnnouncerResponseDto announceResult(String ticketId) {
        if (validator.isBeforeGeneneratingResults(clock)) {
            return ResultAnnouncerResponseDto.builder()
                    .message(WAIT_FOR_RESULTS_MESSAGE.message)
                    .build();
        }

        if (repository.existsByTicketId(ticketId)) {
            if (validator.hasCooldown(ticketId, clock)) {
                return ResultAnnouncerResponseDto.builder()
                        .message(COOLDOWN_MESSAGE.message)
                        .build();
            }
            ResultResponse result = repository.findByTicketId(ticketId);
            ResultResponseDto mappedResult = mapFromResultResponseToDto(result);
            return ResultAnnouncerResponseDto.builder()
                    .responseDto(mappedResult)
                    .message(validator.checkIfPlayerWon(mappedResult))
                    .build();
        }
        PlayerDto playerDto = checkerFacade.findPlayerByTicketId(ticketId);
        if (playerDto == null) {
            return ResultAnnouncerResponseDto.builder()
                    .message(COULDNT_FIND_TICKET_MESSAGE.message)
                    .build();
        }
        ResultResponse response = mapFromPlayerDtoToResultResponse(playerDto);
        repository.save(response);
        ResultResponseDto responseDto = mapFromResultResponseToDto(response);
        return ResultAnnouncerResponseDto.builder()
                .responseDto(responseDto)
                .message(validator.checkIfPlayerWon(responseDto))
                .build();
    }

}
