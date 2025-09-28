package org.maciooo.domain.resultannouncer;

import org.maciooo.domain.resultchecker.ResultCheckerFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ResultAnnouncerConfiguration {
    @Bean
    ResultAnnouncerFacade createFacadeForTests(ResultAnnouncerRepository repository, ResultCheckerFacade checkerFacade, Clock clock) {
        ResultAnnouncerValidator validator = new ResultAnnouncerValidator();
        return new ResultAnnouncerFacade(repository, checkerFacade, clock, validator);
    }
}
