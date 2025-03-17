package org.maciooo.domain.drawdate;

import org.maciooo.domain.drawdate.dto.DrawDateDto;

import java.time.Clock;

public class DrawDateFacade {
    private final DrawDateGenerable drawDateGenerator;

    public DrawDateFacade(Clock clock) {
        this.drawDateGenerator = new DrawDateGenerator(clock);
    }


    public DrawDateDto getNextDrawDate() {
        DrawDate drawDate = drawDateGenerator.calculateNextDrawDate();
        return DrawDateDto.builder()
                .date(drawDate.date())
                .message(drawDate.message())
                .build();
    }

}
