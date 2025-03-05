package org.maciooo.domain.numberreceiver;

import org.maciooo.domain.drawdate.DrawDateFacade;

import java.time.Clock;

class NumberReceiverConfiguration {
    NumberReceiverFacade createFacadeForTest(HashGenerable hashGenerator, Clock clock, NumberReceiverRepository repository) {
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator, repository, drawDateFacade, hashGenerator);
    }

}
