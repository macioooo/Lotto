package org.maciooo.domain.drawdate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

class DrawDateGenerator {
    private static final LocalTime DRAW_DATE_TIME = LocalTime.of(12, 0, 0);
    private static final TemporalAdjuster NEXT_DRAW_DAY = TemporalAdjusters.next(DayOfWeek.SATURDAY);
    private final Clock clock;

    public DrawDateGenerator(Clock clock) {
        this.clock = clock;
    }
    LocalDateTime checkNextDrawDate() {
        LocalDateTime todayDateAndTime = LocalDateTime.now(clock);
        if (isSaturdayBeforeNoon(todayDateAndTime)) {
            return LocalDateTime.of(todayDateAndTime.toLocalDate(), DRAW_DATE_TIME);
        }
        LocalDateTime nextDrawDate = todayDateAndTime.with(NEXT_DRAW_DAY);
        return LocalDateTime.of(nextDrawDate.toLocalDate(), DRAW_DATE_TIME);
    }
    private boolean isSaturdayBeforeNoon(LocalDateTime todayDateAndTime) {
    return todayDateAndTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) && todayDateAndTime.toLocalTime().isBefore(DRAW_DATE_TIME);
    }
}
