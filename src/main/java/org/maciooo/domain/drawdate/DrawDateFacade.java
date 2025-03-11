package org.maciooo.domain.drawdate;

import java.time.Clock;
import java.time.LocalDateTime;

public class DrawDateFacade {
    private final DrawDateGenerator drawDateGenerator;

    public DrawDateFacade(Clock clock) {
        this.drawDateGenerator = new DrawDateGenerator(clock);
    }


    public LocalDateTime getNextDrawDate() {
        return drawDateGenerator.checkNextDrawDate();
    }
    public LocalDateTime calculateDrawDate(LocalDateTime date) {return drawDateGenerator.calculateNextDrawDateByGivenDate(date);}
}
