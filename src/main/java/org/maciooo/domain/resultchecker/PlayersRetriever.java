package org.maciooo.domain.resultchecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class PlayersRetriever {
    private final static int NUMBERS_NEEDED_TO_WIN = 3;

    List<Player> retrieveAllPlayers(List<Ticket> allTicketsByDrawDate, Set<Integer> winningNumbers) {
        return allTicketsByDrawDate.stream()
                .map(ticket -> createPlayer(calculateGuessedNumbers(winningNumbers, ticket), ticket))
                .toList();
    }

    private Set<Integer> calculateGuessedNumbers(Set<Integer> winningNumbers, Ticket ticket) {
        return ticket.numbersFromUser().stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }

    private Player createPlayer(Set<Integer> guessedNumbers, Ticket ticket) {
        return Player.builder()
                .isWinner(isPlayerTheWinner(guessedNumbers))
                .guessedNumbers(guessedNumbers)
                .playerNumbers(ticket.numbersFromUser())
                .drawDate(ticket.drawDate())
                .ticketId(ticket.ticketId())
                .build();
    }

    private boolean isPlayerTheWinner(Set<Integer> guessedNumbers) {
        return guessedNumbers.size()>=NUMBERS_NEEDED_TO_WIN;
    }


}
