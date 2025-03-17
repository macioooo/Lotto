package org.maciooo.domain.resultchecker;

import lombok.Builder;

import java.util.Set;
@Builder
record Player(String ticketId, Set<Integer> playerNumbers, Set<Integer> guessedNumbers,  String drawDate, boolean isWinner){

}
