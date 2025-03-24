package org.maciooo.domain.resultannouncer;

import org.junit.jupiter.api.Test;
import org.maciooo.domain.AdjustableClock;
import org.maciooo.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import org.maciooo.domain.resultannouncer.dto.ResultResponseDto;
import org.maciooo.domain.resultchecker.ResultCheckerFacade;
import org.maciooo.domain.resultchecker.dto.PlayerDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMapper.mapFromPlayerDtoToResultResponse;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMapper.mapFromResultResponseToDto;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMessages.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultAnnouncerFacadeTest {

    ResultCheckerFacade checkerFacade = mock(ResultCheckerFacade.class);

    @Test
    void should_return_wait_for_results_message() {
        //given
        ResultAnnouncerRepository repository = new InMemoryResultAnnouncerRepositoryTestImpl();
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2025, 3, 22, 11, 50, 30).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createFacadeForTests(repository, checkerFacade, clock);
        String expectedMessage = ResultAnnouncerMessages.WAIT_FOR_RESULTS_MESSAGE.message;
        //when
        String message = facade.announceResult("123").message();
        //then
        assertThat(expectedMessage).isEqualTo(message);
    }

    @Test
    void should_return_cooldown_message_when_user_tried_to_check_3_times_his_ticket_in_5_minutes() {
        //given
        ResultAnnouncerRepository repository = new InMemoryResultAnnouncerRepositoryTestImpl();
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2025, 3, 22, 12, 6, 30).atZone(ZoneId.systemDefault()).toInstant().plusSeconds(3600), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createFacadeForTests(repository, checkerFacade, clock);
        ResultResponseDto result = ResultResponseDto.builder()
                .ticketId("001")
                .guessedNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .isWinner(true)
                .drawDate(LocalDateTime.of(2025, 3, 22, 12, 0, 0).toString())
                .build();
        repository.save(ResultAnnouncerMapper.mapFromDtoToResultResponse(result));
        //when
        //first two results should be returned
        ResultAnnouncerResponseDto first = facade.announceResult("001");
        ResultAnnouncerResponseDto second = facade.announceResult("001");
        ResultAnnouncerResponseDto expected = facade.announceResult("001");
        ResultAnnouncerResponseDto expectedFirstAndSecond = new ResultAnnouncerResponseDto(result, YOU_WON_MESSAGE.message);
        //then
        assertThat(first).isEqualTo(expectedFirstAndSecond);
        assertThat(second).isEqualTo(expectedFirstAndSecond);
        assertThat(expected).isEqualTo(new ResultAnnouncerResponseDto(null, COOLDOWN_MESSAGE.message));
     }


    @Test
    void should_return_you_won_message_when_user_is_in_result_announcer_db() {
        //given
        ResultAnnouncerRepository repository = new InMemoryResultAnnouncerRepositoryTestImpl();
        Clock clock = Clock.fixed(LocalDateTime.of(2025, 3, 24, 12, 13, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createFacadeForTests(repository, checkerFacade, clock);
        String expectedMessage = YOU_WON_MESSAGE.message;
        ResultResponseDto result = ResultResponseDto.builder()
                .ticketId("001")
                .guessedNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .isWinner(true)
                .drawDate(LocalDateTime.of(2025, 3, 22, 12, 0, 0).toString())
                .build();
        repository.save(ResultAnnouncerMapper.mapFromDtoToResultResponse(result));
        //when
        String message = facade.announceResult("001").message();
        //then
        assertThat(message).isEqualTo(expectedMessage);
    }

    @Test
    void should_return_you_lose_message_when_user_is_in_result_announcer_db() {
        //given
        ResultAnnouncerRepository repository = new InMemoryResultAnnouncerRepositoryTestImpl();
        Clock clock = Clock.fixed(LocalDateTime.of(2025, 3, 24, 12, 13, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createFacadeForTests(repository, checkerFacade, clock);
        String expectedMessage = YOU_LOSE_MESSAGE.message;
        ResultResponseDto result = ResultResponseDto.builder()
                .ticketId("001")
                .guessedNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .isWinner(false)
                .drawDate(LocalDateTime.of(2025, 3, 22, 12, 0, 0).toString())
                .build();
        repository.save(ResultAnnouncerMapper.mapFromDtoToResultResponse(result));
        //when
        String message = facade.announceResult("001").message();
        //then
        assertThat(message).isEqualTo(expectedMessage);
    }

    @Test
    void should_return_couldnt_find_ticket_message() {
        //given
        ResultAnnouncerRepository repository = new InMemoryResultAnnouncerRepositoryTestImpl();
        Clock clock = Clock.fixed(LocalDateTime.of(2025, 3, 24, 12, 13, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createFacadeForTests(repository, checkerFacade, clock);
        ResultAnnouncerResponseDto expectedResponse = ResultAnnouncerResponseDto.builder()
                .message(COULDNT_FIND_TICKET_MESSAGE.message)
                .build();
        when(checkerFacade.findPlayerByTicketId("001")).thenReturn(null);
        //when
        ResultAnnouncerResponseDto responseDto = facade.announceResult("001");
        //then
        assertThat(responseDto).isEqualTo(expectedResponse);
    }

    @Test
    void should_create_result_announcer_response_from_player_when_ticket_is_not_saved_in_result_announcer() {
        //given
        ResultAnnouncerRepository repository = new InMemoryResultAnnouncerRepositoryTestImpl();
        Clock clock = Clock.fixed(LocalDateTime.of(2025, 3, 24, 12, 13, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade facade = new ResultAnnouncerConfiguration().createFacadeForTests(repository, checkerFacade, clock);
        PlayerDto playerDto =
                PlayerDto.builder()
                        .isWinner(true)
                        .guessedNumbers(Set.of(1, 2, 3, 4, 5))
                        .playerNumbers(Set.of(1, 2, 3, 4, 5, 66))
                        .ticketId("001")
                        .drawDate(LocalDateTime.of(2025, 3, 24, 12, 0, 0).toString())
                        .build();
        when(checkerFacade.findPlayerByTicketId("001")).thenReturn(playerDto);
        ResultResponse expectedResultResponse = mapFromPlayerDtoToResultResponse(playerDto);
        ResultResponseDto expectedResultResponseDto = mapFromResultResponseToDto(expectedResultResponse);
        //when
        ResultAnnouncerResponseDto responseDto = facade.announceResult("001");
        //then
        assertThat(repository.findByTicketId("001")).isEqualTo(expectedResultResponse);
        assertThat(responseDto).isEqualTo(new ResultAnnouncerResponseDto(expectedResultResponseDto, YOU_WON_MESSAGE.message));
    }
}