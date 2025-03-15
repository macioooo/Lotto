package org.maciooo.domain.numberreceiver;

import org.junit.jupiter.api.Test;
import org.maciooo.domain.AdjustableClock;
import org.maciooo.domain.drawdate.DrawDateFacade;
import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
import org.maciooo.domain.numberreceiver.dto.TicketDto;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class NumberReceiverFacadeTest {
    Clock clock = Clock.system(TimeZone.getDefault().toZoneId());
    DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    private final NumberReceiverRepository numberReceiverRepository = new InMemoryNumberReceiverRepositoryTestImpl();

    @Test
    public void should_return_success_when_user_gave_6_numbers() {
        //given
        AdjustableClock adjustableClock = new AdjustableClock(LocalDateTime.of(2024, 2, 16, 21, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerator, adjustableClock, numberReceiverRepository);
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 3, 4, 5, 6);
        when(drawDateFacade.getNextDrawDate()).thenReturn(LocalDateTime.of(2024,2, 17, 12, 0, 0));
        TicketDto expectedTicket = TicketDto.builder()
                .ticketId(hashGenerator.getHash())
                .numbersFromUser(numbersGivenByUser)
                .drawDate(drawDateFacade.getNextDrawDate())
                .build();
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        InputNumberResultDto expectedResult = new InputNumberResultDto(NumberReceiverValidationMessages.SUCCESS.message, expectedTicket);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_less_or_more_numbers_message_when_user_gave_less_than_6_numbers() {
        //given
        HashGenerable hashGenerable = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerable, clock, numberReceiverRepository);
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumberResultDto expectedResult = new InputNumberResultDto(NumberReceiverValidationMessages.LESS_OR_MORE_THAN_6_NUMBERS_GIVEN.message, null);
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_less_or_more_numbers_message_when_user_gave_more_than_6_numbers() {
        //given
        HashGenerable hashGenerable = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerable, clock, numberReceiverRepository);
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        InputNumberResultDto expectedResult = new InputNumberResultDto(NumberReceiverValidationMessages.LESS_OR_MORE_THAN_6_NUMBERS_GIVEN.message, null);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_not_in_range_when_user_gave_numbers_out_of_range_1_to_99() {
        //given
        HashGenerable hashGenerable = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerable, clock, numberReceiverRepository);
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 300, 4, 5, 6);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        InputNumberResultDto expectedResult = new InputNumberResultDto(NumberReceiverValidationMessages.NOT_IN_RANGE.message, null);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_not_in_range_and_less_or_more_numbers_messages_when_user_gave_numbers_out_of_range_1_to_99_and_less_than_6() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerator, clock, numberReceiverRepository);
        Set<Integer> numbersGivenByUser = Set.of(1,2,100,3,4);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        String expectedMessage = NumberReceiverValidationMessages.LESS_OR_MORE_THAN_6_NUMBERS_GIVEN.message + ", " + NumberReceiverValidationMessages.NOT_IN_RANGE.message;
        InputNumberResultDto expectedResult = new InputNumberResultDto(expectedMessage, null);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_save_to_database_when_user_gave_6_numbers() {
        //given
        AdjustableClock adjustableClock = new AdjustableClock(LocalDateTime.of(2024, 2, 18, 21, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerator, adjustableClock, numberReceiverRepository);
        Set<Integer> nubersGivenByUser = Set.of(1, 2, 3, 4, 5, 6);
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(nubersGivenByUser);
        //when
        List<TicketDto> ticketDtos = numberReceiverFacade.getAllTicketsForNextDrawDate();
        //then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .numbersFromUser(result.ticketDto().numbersFromUser())
                        .drawDate(result.ticketDto().drawDate())
                        .ticketId(result.ticketDto().ticketId())
                        .build()

        );
    }

    @Test
    public void should_return_only_tickets_with_current_draw_date() {
        //given
        //thursday 15:25
        AdjustableClock fixedDate = new AdjustableClock(LocalDateTime.of(2025, 2, 27, 15, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        HashGenerable hashGenerable = new HashGenerator();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerable, fixedDate, numberReceiverRepository);
        InputNumberResultDto firstTicket = numberReceiverFacade.inputNumbers(Set.of(10, 15, 12, 14, 16, 18));
        fixedDate.plusDays(1);
        //friday 15:25
        InputNumberResultDto secondTicket = numberReceiverFacade.inputNumbers(Set.of(9, 55, 21, 34, 16, 13));
        fixedDate.plusDays(1);

        //saturday 15:25
        InputNumberResultDto thirdTicket = numberReceiverFacade.inputNumbers(Set.of(88,77,6,55,44,33));
        fixedDate.plusDays(1);

        //sunday 15:25

        InputNumberResultDto fourthTicket = numberReceiverFacade.inputNumbers(Set.of(11,27,33,44,54,63));
        TicketDto expectedTicketOne = thirdTicket.ticketDto();
        TicketDto expectedTicketTwo = fourthTicket.ticketDto();
        //when
        List<TicketDto> allExpectedTickets = numberReceiverFacade.getAllTicketsForNextDrawDate();
        //then
        assertThat(allExpectedTickets).containsOnly(expectedTicketOne, expectedTicketTwo);
    }
    @Test
    public void should_return_no_tickets_on_current_draw_date() {
        //given
        //friday 15:25
        AdjustableClock fixedDate = new AdjustableClock(LocalDateTime.of(2025, 2, 28, 15, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        HashGenerable hashGenerator = new HashGenerator();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerator, fixedDate, numberReceiverRepository);
        numberReceiverFacade.inputNumbers(Set.of(1,2,3,4,5,6));
        numberReceiverFacade.inputNumbers(Set.of(10,20,30,40,50,60));
        fixedDate.plusDays(1);
        //saturday 15:25
        //when
        List<TicketDto> allTickets = numberReceiverFacade.getAllTicketsForNextDrawDate();
        //then
        assertThat(allTickets).isEmpty();
    }
    @Test
    public void should_return_empty_list_when_trying_to_see_future_draw_dates() {
        //next draw date: 1.03.2025 12:00
        //given
        AdjustableClock fixedDate = new AdjustableClock(LocalDateTime.of(2025, 2, 28, 15, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerator, fixedDate, numberReceiverRepository);
        //then
        assertThat(numberReceiverFacade.getAllTicketsForNextDrawDate()).isEmpty();
    }
    @Test
    public void should_return_previous_week_draw_date_tickets() {
        //given
        //thursday 15:25
        AdjustableClock fixedDate = new AdjustableClock(LocalDateTime.of(2025, 2, 27, 15, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        HashGenerable hashGenerable = new HashGenerator();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createFacadeForTest(hashGenerable, fixedDate, numberReceiverRepository);
        InputNumberResultDto firstTicket = numberReceiverFacade.inputNumbers(Set.of(10, 15, 12, 14, 16, 18));
        fixedDate.plusDays(1);
        //friday 15:25
        InputNumberResultDto secondTicket = numberReceiverFacade.inputNumbers(Set.of(9, 55, 21, 34, 16, 13));
        fixedDate.plusDays(1);

        //saturday 15:25
        InputNumberResultDto thirdTicket = numberReceiverFacade.inputNumbers(Set.of(88,77,6,55,44,33));
        fixedDate.plusDays(1);

        //sunday 15:25

        InputNumberResultDto fourthTicket = numberReceiverFacade.inputNumbers(Set.of(11,27,33,44,54,63));
        TicketDto expectedTicketOne = firstTicket.ticketDto();
        TicketDto expectedTicketTwo = secondTicket.ticketDto();

        //when
        List<TicketDto> allExpectedTickets = numberReceiverFacade.getAllTicketsByDrawDate(expectedTicketOne.drawDate());
        //then
        assertThat(allExpectedTickets).containsOnly(expectedTicketOne, expectedTicketTwo);
    }
}


