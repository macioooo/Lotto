package org.maciooo.domain.resultannouncer.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ResultResponseDto(String ticketId, Set<Integer> guessedNumbers, String drawDate, boolean isWinner) {
}
