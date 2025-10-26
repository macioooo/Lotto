package org.maciooo.domain.resultchecker;

import lombok.AllArgsConstructor;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numberreceiver.NumberReceiverFacade;
import org.maciooo.domain.numberreceiver.dto.TicketDto;
import org.maciooo.domain.resultchecker.dto.AllPlayersDto;
import org.maciooo.domain.resultchecker.dto.PlayerDto;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {
    private final PlayerRepository playerRepository;
    private final PlayersRetriever playersRetriever;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final NumberReceiverFacade numberReceiverFacade;

    public AllPlayersDto generateWinners() {
        List<TicketDto> listOfTicketsDto = numberReceiverFacade.getAllTicketsForNextDrawDate();
        List<Ticket> listOfTicketsMapped = ResultCheckerMapper.mapToTicketList(listOfTicketsDto);
        Set<Integer> winningNumbers = numberGeneratorFacade.generateWinningNumbers().winningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return AllPlayersDto.builder()
                    .message("Failed to retrieve winning numbers")
                    .build();
        }
        List<Player> playersList = playersRetriever.retrieveAllPlayers(listOfTicketsMapped, winningNumbers);
        playerRepository.saveAll(playersList);
        return AllPlayersDto.builder()
                .message("Players list was sucessfully retrieved!")
                .results(ResultCheckerMapper.mapPlayersToResult(playersList))
                .build();
    }

    public PlayerDto findPlayerByTicketId(String ticketId) {
        return playerRepository.findPlayerByTicketId(ticketId).map(player -> PlayerDto.builder()
                .playerNumbers(player.playerNumbers())
                .guessedNumbers(player.guessedNumbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .ticketId(player.ticketId())
                .build())
                .orElseThrow(() -> new PlayerNotFoundException("Sorry, we couldn't find the ticket with ID: " + ticketId + ". Check if it's correct and try again"));
    }


}
