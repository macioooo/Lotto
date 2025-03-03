package org.maciooo.domain.numberreceiver;

import org.junit.jupiter.api.Test;
import org.maciooo.domain.AdjustableClock;
import org.maciooo.domain.drawdate.DrawDateFacade;
import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
import org.maciooo.domain.numberreceiver.dto.TicketDto;

import java.time.*;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    //dla każdego przypadku tworzymy nową fasade
    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2024, 2, 18, 21, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryTestImpl(),
            new DrawDateFacade(clock)
    );
    @Test
    public void should_return_success_when_user_gave_6_numbers() {
        //given
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        assertThat(result.message()).isEqualTo("success");
    }
    @Test
    public void should_return_falied_when_user_gave_less_than_6_numbers() {
        //given
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        assertThat(result.message()).isEqualTo("failed");
    }
    @Test
    public void should_return_falied_when_user_gave_more_than_6_numbers() {
        //given
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        assertThat(result.message()).isEqualTo("failed");
    }
    @Test
    public void should_return_failed_when_user_gave_numbers_out_of_range_1_to_99() {
        //given
        Set<Integer> numbersGivenByUser = Set.of(1, 2, 300, 4, 5, 6);
        //when
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersGivenByUser);
        //then
        assertThat(result.message()).isEqualTo("failed");
    }
    @Test
    public void should_save_to_database_when_user_gave_6_numbers() {
        //given
        Set<Integer> nubersGivenByUser = Set.of(1,2,3,4,5,6);
        InputNumberResultDto result = numberReceiverFacade.inputNumbers(nubersGivenByUser);
        //when
        LocalDateTime drawDate = LocalDateTime.of(2024, 2, 24, 12, 0, 0);
        List<TicketDto> ticketDtos = numberReceiverFacade.userNumbers(drawDate);
        //then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .numbersFromUser(result.numbersGivenByUser())
                        .drawDate(drawDate)
                        .ticketId(result.ticketId())
                        .build()

        );
    }


}