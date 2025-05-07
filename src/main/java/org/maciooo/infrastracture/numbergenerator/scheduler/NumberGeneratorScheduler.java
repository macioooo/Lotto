package org.maciooo.infrastracture.numbergenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
class NumberGeneratorScheduler {
    private final NumberGeneratorFacade numberGeneratorFacade;

    @Scheduled(cron = "${lotto.number-generator.lotteryRunOccurrence}")
    public void f() {
        log.info("Started scheduling");
        WinningNumbersDto  winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        log.info("Winning numbers: " + winningNumbersDto.winningNumbers());
        log.info("Next draw date: " + winningNumbersDto.drawDate());
    }
}
