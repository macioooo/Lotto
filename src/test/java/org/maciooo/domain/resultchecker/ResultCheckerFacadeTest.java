package org.maciooo.domain.resultchecker;

import org.junit.jupiter.api.Test;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;
import org.maciooo.domain.numberreceiver.NumberReceiverFacade;
import org.maciooo.domain.numberreceiver.dto.TicketDto;
import org.maciooo.domain.resultchecker.dto.AllPlayersDto;
import org.maciooo.domain.resultchecker.dto.PlayerDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultCheckerFacadeTest {
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
    PlayerRepository playerRepository = new InMemoryPlayerRepositoryTestImpl();

    @Test
    void should_return_3_out_of_3_players_and_retrieve_success_message() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .drawDate(drawDate)
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        when(numberReceiverFacade.getAllTicketsForNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                .ticketId("001")
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 55, 66))
                                .ticketId("002")
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 44, 55, 66))
                                .ticketId("003")
                                .drawDate(drawDate)
                                .build()
                )
        );
        //when
        AllPlayersDto allPlayers = resultCheckerFacade.generateWinners();
        //then
        List<PlayerDto> providedPlayers = allPlayers.results();
        PlayerDto playerOne = PlayerDto.builder()
                .isWinner(true)
                .playerNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .guessedNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .ticketId("001")
                .drawDate(drawDate)
                .build();
        PlayerDto playerTwo = PlayerDto.builder()
                .isWinner(true)
                .playerNumbers(Set.of(1, 2, 3, 4, 55, 66))
                .guessedNumbers(Set.of(1, 2, 3, 4))
                .ticketId("002")
                .drawDate(drawDate)
                .build();
        PlayerDto playerThree = PlayerDto.builder()
                .isWinner(true)
                .playerNumbers(Set.of(1, 2, 3, 44, 55, 66))
                .guessedNumbers(Set.of(1, 2, 3))
                .ticketId("003")
                .drawDate(drawDate)
                .build();
        assertThat(providedPlayers).containsOnly(playerOne, playerTwo, playerThree);
        assertThat(allPlayers.message()).isEqualTo("Players list was sucessfully retrieved!");
    }

    @Test
    void should_return_that_both_players_are_winners() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .drawDate(drawDate)
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        when(numberReceiverFacade.getAllTicketsForNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                .ticketId("001")
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 55, 66))
                                .ticketId("002")
                                .drawDate(drawDate)
                                .build())
        );
        //when
        AllPlayersDto allPlayers = resultCheckerFacade.generateWinners();
        //then
        List<PlayerDto> providedPlayers = allPlayers.results();
        assertThat(providedPlayers.stream().allMatch(PlayerDto::isWinner)).isTrue();
    }
    @Test
    void should_return_that_only_second_player_is_winner() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .drawDate(drawDate)
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        when(numberReceiverFacade.getAllTicketsForNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                .ticketId("001")
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 33, 44, 55, 66))
                                .ticketId("002")
                                .drawDate(drawDate)
                                .build())
        );
        //when
        resultCheckerFacade.generateWinners();
        PlayerDto playerOne = resultCheckerFacade.findPlayerByTicketId("001");
        PlayerDto playerTwo = resultCheckerFacade.findPlayerByTicketId("002");
        //then
        assertThat(playerOne.isWinner()).isTrue();
        assertThat(playerTwo.isWinner()).isFalse();
    }


    @Test
    void should_return_failed_to_retrieve_winning_numbers_when_winning_numbers_is_empty() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Collections.emptySet())
                .drawDate(drawDate)
                .build());
        //when
        String result = resultCheckerFacade.generateWinners().message();
        //then
        assertThat(result).isEqualTo("Failed to retrieve winning numbers");
    }

    @Test
    void should_return_failed_to_retrieve_winning_numbers_when_winning_numbers_is_null() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(null)
                .drawDate(drawDate)
                .build());
        //when
        String result = resultCheckerFacade.generateWinners().message();
        //then
        assertThat(result).isEqualTo("Failed to retrieve winning numbers");
    }

    @Test
    void should_return_player_by_id() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberReceiverFacade.getAllTicketsForNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                .ticketId("001")
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 55, 66))
                                .ticketId("002")
                                .drawDate(drawDate)
                                .build())
        );
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .drawDate(drawDate)
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        resultCheckerFacade.generateWinners();
        PlayerDto generatedPlayer = PlayerDto.builder()
                .playerNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .guessedNumbers(Set.of(1,2,3,4,5,6))
                .isWinner(true)
                .ticketId("001")
                .drawDate(drawDate)
                .build();
        //when
        PlayerDto expectedPlayer = resultCheckerFacade.findPlayerByTicketId("001");
        //then
        assertThat(expectedPlayer).isEqualTo(generatedPlayer);
    }

    @Test
    void should_return_exception_player_not_found_when_finding_player_by_id() {
        //given
        String drawDate = LocalDateTime.of(2025, 3, 17, 12, 50).toString();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createFacadeForTest(numberReceiverFacade, numberGeneratorFacade, playerRepository);
        when(numberReceiverFacade.getAllTicketsForNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .numbersFromUser(Set.of(1, 2, 3, 4, 5, 6))
                                .ticketId("001")
                                .drawDate(drawDate)
                                .build()
        ));
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .drawDate(drawDate)
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        resultCheckerFacade.generateWinners();
        //when
        //then
        assertThrows(PlayerNotFound.class, () -> resultCheckerFacade.findPlayerByTicketId("002"), "Player not found.");
    }

}