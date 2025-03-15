package org.maciooo.domain.numbergenerator;

import org.junit.jupiter.api.Test;
import org.maciooo.domain.AdjustableClock;
import org.maciooo.domain.drawdate.DrawDateFacade;
import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumberGeneratorFacadeTest {
    DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    NumberGeneratorValidator numberGeneratorValidator = new NumberGeneratorValidator();
    NumberGeneratorRepository numberGeneratorRepository = new InMemoryNumberGeneratorRepositoryTestImpl();
    Clock clock = Clock.system(TimeZone.getDefault().toZoneId());
    @Test
    public void should_generate_6_numbers() {
        //given
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, clock, numberGeneratorValidator, numberGeneratorRepository);
        //when
        WinningNumbersDto winningNumbers = numberGeneratorFacade.generateWinningNumbers();
        Set<Integer> result =  winningNumbers.winningNumbers();
        //then
        assertEquals(result.size(), 6);
    }

    @Test
    public void should_throw_winning_numbers_out_of_range_exception_when_numbers_are_lower_than_1() {
        //given
        NumberGenerable numberGenerator = new NumberGeneratorTestImpl(Set.of(1,2,3,4,5,0));
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, clock, numberGeneratorValidator, numberGeneratorRepository);
        //then
        assertThrows(WinningNumbersOutOfRange.class, () -> numberGeneratorFacade.generateWinningNumbers(), "Numbers are out of range 1-99");
    }
    @Test
    public void should_throw_winning_numbers_out_of_range_exception_when_numbers_are_bigger_than_99() {
        //given
        NumberGenerable numberGenerator = new NumberGeneratorTestImpl(Set.of(1,2,3,4,5,100));
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, clock, numberGeneratorValidator, numberGeneratorRepository);
        //then
        assertThrows(WinningNumbersOutOfRange.class, () -> numberGeneratorFacade.generateWinningNumbers(), "Numbers are out of range 1-99");
    }

    @Test
    public void should_return_current_winning_numbers() {
        //given
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, clock, numberGeneratorValidator, numberGeneratorRepository);
        //when
        when(drawDateFacade.getNextDrawDate()).thenReturn(LocalDateTime.now(clock));
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        LocalDateTime drawDate = winningNumbersDto.drawDate();
        //then
        assertEquals(winningNumbersDto, numberGeneratorFacade.getWinningNumbersByDrawDate(drawDate));
    }
    @Test
    public void should_return_previous_week_winning_numbers() {
        AdjustableClock adjustableClock = new AdjustableClock(LocalDateTime.of(2025, 2, 14, 21, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, adjustableClock, numberGeneratorValidator, numberGeneratorRepository);
        WinningNumbersDto winningNumbersOne = numberGeneratorFacade.generateWinningNumbers();
        adjustableClock.plusDays(1);
        WinningNumbersDto winningNumbersTwo = numberGeneratorFacade.generateWinningNumbers();
        //when
        WinningNumbersDto expectedWinningNumbers = numberGeneratorFacade.getWinningNumbersByDrawDate(winningNumbersOne.drawDate());
        //then
        assertEquals(expectedWinningNumbers, winningNumbersOne);
    }
    @Test
    public void should_return_all_winning_numbers() {
        //given
        //friday
        AdjustableClock adjustableClock = new AdjustableClock(LocalDateTime.of(2025, 2, 14, 21, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, adjustableClock, numberGeneratorValidator, numberGeneratorRepository);
        WinningNumbersDto winningNumbersOne = numberGeneratorFacade.generateWinningNumbers();
        adjustableClock.plusDays(1);
        WinningNumbersDto winningNumbersTwo = numberGeneratorFacade.generateWinningNumbers();
        //when
        List<WinningNumbersDto> winningNumbersList = numberGeneratorFacade.getAllWinningNumbers();
        //then
        assertThat(winningNumbersList).containsOnly(winningNumbersOne, winningNumbersTwo);

    }

    @Test
    public void should_throw_winning_numbers_already_generated_when_generating_winning_numbers_for_the_second_time() {
        //given
        AdjustableClock adjustableClock = new AdjustableClock(LocalDateTime.of(2025, 2, 14, 21, 25, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, adjustableClock, numberGeneratorValidator, numberGeneratorRepository);
        WinningNumbersDto generatedNumbersOne = numberGeneratorFacade.generateWinningNumbers();
        //when//then
        assertThrows(WinningNumbersAlreadyGenerated.class, () -> numberGeneratorFacade.generateWinningNumbers(), "Numbers were already generated for this week!");
    }

    @Test
    public void should_return_winning_numbers_with_empty_set_when_numbers_werent_generated_for_draw_date() {
        //given
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, clock, numberGeneratorValidator, numberGeneratorRepository);
        //when
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.getWinningNumbersByDrawDate(LocalDateTime.now());
        //then
        assertEquals(Collections.emptySet(), winningNumbersDto.winningNumbers());
    }

    @Test
    public void should_return_empty_list_when_no_winning_numbers_were_ever_generated() {
        //given
        NumberGenerable numberGenerator = new NumberGenerator();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().createFacadeForTests(numberGenerator, clock, numberGeneratorValidator, numberGeneratorRepository);
        //when
        List<WinningNumbersDto> listOfWinningNumbers = numberGeneratorFacade.getAllWinningNumbers();
        //then
        assertEquals(Collections.emptyList(), listOfWinningNumbers);
    }

}