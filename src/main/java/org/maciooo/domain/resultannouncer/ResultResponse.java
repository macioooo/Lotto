package org.maciooo.domain.resultannouncer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Builder
@Document
record ResultResponse(@Id String ticketId, Set<Integer> guessedNumbers, String drawDate, boolean isWinner) {
}
