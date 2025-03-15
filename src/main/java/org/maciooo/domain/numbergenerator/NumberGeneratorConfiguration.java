package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.drawdate.DrawDateFacade;

import java.time.Clock;

class NumberGeneratorConfiguration {
    NumberGeneratorFacade createFacadeForTests(NumberGenerable numberGenerator, Clock clock, NumberGeneratorValidator numberGeneratorValidator, NumberGeneratorRepository numberGeneratorRepository) {
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        return new NumberGeneratorFacade(numberGenerator, drawDateFacade, numberGeneratorValidator, numberGeneratorRepository);
    }

}
