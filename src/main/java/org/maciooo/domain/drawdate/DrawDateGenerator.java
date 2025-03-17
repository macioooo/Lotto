package org.maciooo.domain.drawdate;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

class DrawDateGenerator implements DrawDateGenerable {
    private static final LocalTime DRAW_DATE_TIME = LocalTime.of(12, 0, 0);
    private static final TemporalAdjuster NEXT_DRAW_DAY = TemporalAdjusters.next(DayOfWeek.SATURDAY);
    private final Clock clock;

    DrawDateGenerator(Clock clock) {
        if (clock == null) {
            throw new NullClockException("Clock cannot be null");
        }
        this.clock = clock;
    }


    public DrawDate calculateNextDrawDate() {
        LocalDateTime date = LocalDateTime.now(clock);
        if (isSaturdayBeforeNoon(date)) {
            return DrawDateMapper.mapLocalDateTimeToDrawDate(LocalDateTime.of(date.toLocalDate(), DRAW_DATE_TIME));
        }
        LocalDateTime drawDate = date.with(NEXT_DRAW_DAY);
        LocalDateTime result = LocalDateTime.of(drawDate.toLocalDate(), DRAW_DATE_TIME);
        return DrawDateMapper.mapLocalDateTimeToDrawDate(result);
    }

    private boolean isSaturdayBeforeNoon(LocalDateTime todayDateAndTime) {
        return todayDateAndTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) && todayDateAndTime.toLocalTime().isBefore(DRAW_DATE_TIME);
    }
}
