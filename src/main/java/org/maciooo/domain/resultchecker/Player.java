package org.maciooo.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
@Builder
record Player(String ticketId, Set<Integer> playerNumbers, Set<Integer> guessedNumbers,  LocalDateTime drawDate, boolean isWinner){

}
