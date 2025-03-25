package org.maciooo.domain.resultannouncer;

import org.maciooo.domain.resultannouncer.dto.ResultResponseDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.maciooo.domain.resultannouncer.ResultAnnouncerMessages.YOU_LOSE_MESSAGE;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMessages.YOU_WON_MESSAGE;

class ResultAnnouncerValidator {
    private final static LocalTime ANNOUNCE_RESULTS_TIME = LocalTime.of(12, 5, 0);
    private final Map<String, LocalDateTime> rateLimiting = new ConcurrentHashMap<>();

    String checkIfPlayerWon(ResultResponseDto response) {
        if (response.isWinner()) {
            return YOU_WON_MESSAGE.message;
        }
        return YOU_LOSE_MESSAGE.message;
    }

    boolean isBeforeGeneneratingResults(Clock clock) {
        return LocalTime.now(clock).isBefore(ANNOUNCE_RESULTS_TIME);
    }

    boolean hasCooldown(String ticketId, Clock clock) {
        if (!rateLimiting.containsKey(ticketId)) {
            addNewTicketToRateLimit(ticketId, clock);
        } else return rateLimiting.containsKey(ticketId) && ticketHasCooldown(ticketId, clock);
        return false;
    }


    private void addNewTicketToRateLimit(String ticketId, Clock clock) {
        rateLimiting.put(ticketId, LocalDateTime.now(clock).plusMinutes(5));
    }

    private boolean ticketHasCooldown(String ticketId, Clock clock) {
        return LocalDateTime.now(clock).isBefore(rateLimiting.get(ticketId));
    }
}
