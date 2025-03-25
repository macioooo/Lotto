package org.maciooo.domain.resultannouncer;

import lombok.Builder;

import java.util.Set;

@Builder
record ResultResponse(String ticketId, Set<Integer> guessedNumbers, String drawDate, boolean isWinner) {
}
