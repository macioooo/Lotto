package org.maciooo.domain.resultchecker.dto;

import lombok.Builder;

import java.util.Set;
@Builder
public record PlayerDto(String ticketId, Set<Integer> playerNumbers, Set<Integer> guessedNumbers, String drawDate, boolean isWinner) {
}
