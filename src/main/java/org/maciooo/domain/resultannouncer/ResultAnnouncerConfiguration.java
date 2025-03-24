package org.maciooo.domain.resultannouncer;

import org.maciooo.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

class ResultAnnouncerConfiguration {
    ResultAnnouncerFacade createFacadeForTests(ResultAnnouncerRepository repository, ResultCheckerFacade checkerFacade, Clock clock) {
        ResultAnnouncerValidator validator = new ResultAnnouncerValidator();
        return new ResultAnnouncerFacade(repository, checkerFacade, clock, validator);
    }
}
