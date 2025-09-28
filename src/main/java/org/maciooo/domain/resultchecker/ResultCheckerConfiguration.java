package org.maciooo.domain.resultchecker;

import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ResultCheckerConfiguration {
    @Bean
    ResultCheckerFacade createFacadeForTest(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, PlayerRepository playerRepository) {
        PlayersRetriever playersRetriever = new PlayersRetriever();
        return new ResultCheckerFacade(playerRepository, playersRetriever, numberGeneratorFacade,  numberReceiverFacade);
    }
}
