package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.drawdate.DrawDateFacade;

import java.time.Clock;

class NumberGeneratorConfiguration {
    NumberGeneratorFacade createFacadeForTests(RandomNumberGenerable numberGenerator, Clock clock , NumberGeneratorRepository numberGeneratorRepository) {
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        NumberGeneratorValidator numberGeneratorValidator = new NumberGeneratorValidator();
        return new NumberGeneratorFacade(numberGenerator, drawDateFacade, numberGeneratorValidator, numberGeneratorRepository);
    }

}
