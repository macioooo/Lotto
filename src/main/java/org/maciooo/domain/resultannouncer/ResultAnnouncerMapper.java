package org.maciooo.domain.resultannouncer;

import org.maciooo.domain.resultannouncer.dto.ResultResponseDto;
import org.maciooo.domain.resultchecker.dto.PlayerDto;

class ResultAnnouncerMapper {

    static ResultResponse mapFromPlayerDtoToResultResponse(PlayerDto playerDto) {
        return ResultResponse.builder()
                .guessedNumbers(playerDto.guessedNumbers())
                .drawDate(playerDto.drawDate())
                .isWinner(playerDto.isWinner())
                .ticketId(playerDto.ticketId())
                .build();
    }

    static ResultResponseDto mapFromResultResponseToDto(ResultResponse resultResponse) {
        return ResultResponseDto.builder()
                .guessedNumbers(resultResponse.guessedNumbers())
                .ticketId(resultResponse.ticketId())
                .drawDate(resultResponse.drawDate())
                .isWinner(resultResponse.isWinner())
                .build();
    }

    static ResultResponse mapFromDtoToResultResponse(ResultResponseDto resultResponseDto) {
        return ResultResponse.builder()
                .guessedNumbers(resultResponseDto.guessedNumbers())
                .ticketId(resultResponseDto.ticketId())
                .drawDate(resultResponseDto.drawDate())
                .isWinner(resultResponseDto.isWinner())
                .build();
    }
}
