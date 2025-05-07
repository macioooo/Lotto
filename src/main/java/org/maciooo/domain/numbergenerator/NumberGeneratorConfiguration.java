package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.drawdate.DrawDateFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.List;

@Configuration
class NumberGeneratorConfiguration {

    @Bean
    NumberGeneratorRepository numberGeneratorRepository() {
        return new NumberGeneratorRepository() {
            @Override
            public WinningNumbers save(WinningNumbers winningNumbers) {
                return null;
            }

            @Override
            public List<WinningNumbers> findAllWinningNumbers() {
                return null;
            }

            @Override
            public WinningNumbers findWinningNumbersByDrawDate(String drawDate) {
                return null;
            }
        };
    }

    @Bean
    NumberGeneratorFacade numberGeneratorFacade(NumberGeneratorRepository numberGeneratorRepository, NumberGeneratorFacadeConfigProperties properties, RandomNumberGenerable randomNumberGenerator, Clock clock) {
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        NumberGeneratorValidator validator = new NumberGeneratorValidator();
        return new NumberGeneratorFacade(randomNumberGenerator, drawDateFacade, validator, numberGeneratorRepository, properties);
    }

    NumberGeneratorFacade createFacadeForTests(RandomNumberGenerable numberGenerator, Clock clock, NumberGeneratorRepository numberGeneratorRepository) {
        NumberGeneratorFacadeConfigProperties properties = NumberGeneratorFacadeConfigProperties.builder()
                .count(6)
                .lowerBand(1)
                .upperBand(99)
                .build();
        return numberGeneratorFacade(numberGeneratorRepository, properties, numberGenerator, clock);
    }

}
