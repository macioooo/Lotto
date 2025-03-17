package org.maciooo.domain.resultchecker;

import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numberreceiver.NumberReceiverFacade;

class ResultCheckerConfiguration {
     ResultCheckerFacade createFacadeForTest(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, PlayerRepository playerRepository) {
        PlayersRetriever playersRetriever = new PlayersRetriever();
        return new ResultCheckerFacade(playerRepository, playersRetriever, numberGeneratorFacade,  numberReceiverFacade);
    }
}
