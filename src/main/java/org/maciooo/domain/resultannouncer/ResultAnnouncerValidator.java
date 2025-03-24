package org.maciooo.domain.resultannouncer;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.maciooo.domain.resultannouncer.dto.ResultResponseDto;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.maciooo.domain.resultannouncer.ResultAnnouncerMessages.YOU_LOSE_MESSAGE;
import static org.maciooo.domain.resultannouncer.ResultAnnouncerMessages.YOU_WON_MESSAGE;

class ResultAnnouncerValidator {
    private final static LocalTime ANNOUNCE_RESULTS_TIME = LocalTime.of(12, 5, 0);
    private final Map<String, Bucket> ticketBuckets = new ConcurrentHashMap<>();

    String checkIfPlayerWon(ResultResponseDto response) {
        if (response.isWinner()) {
            return YOU_WON_MESSAGE.message;
        }
        return YOU_LOSE_MESSAGE.message;
    }

    boolean isBeforeGeneneratingResults(Clock clock) {
        return LocalTime.now(clock).isBefore(ANNOUNCE_RESULTS_TIME);
    }

    boolean hasCooldown(String ticketId) {
        Bucket bucket = ticketBuckets.computeIfAbsent(ticketId, key -> createNewBucket());
        bucket.tryConsume(1);
        return bucket.getAvailableTokens() <= 0;
    }


    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(5))))
                .build();
    }
}
