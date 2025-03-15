package org.maciooo.domain.numberreceiver;

import org.maciooo.domain.drawdate.DrawDateFacade;

import java.time.Clock;

class NumberReceiverConfiguration {
    NumberReceiverFacade createFacadeForTest(HashGenerable hashGenerator, Clock clock, NumberReceiverRepository repository) {
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        NumberReceiverValidator numberValidator = new NumberReceiverValidator();
        return new NumberReceiverFacade(numberValidator, repository, drawDateFacade, hashGenerator);
    }

}
