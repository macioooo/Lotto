package org.maciooo.domain.drawdate;

import java.time.LocalDateTime;

class DrawDateMapper {
    static DrawDate mapLocalDateTimeToDrawDate(LocalDateTime date) {
        return DrawDate.builder()
                .date(date.toString())
                .build();
    }
}
