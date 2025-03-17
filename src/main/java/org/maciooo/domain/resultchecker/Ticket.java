package org.maciooo.domain.resultchecker;

import lombok.Builder;

import java.util.Set;
@Builder
record Ticket(String ticketId, String drawDate, Set<Integer> numbersFromUser) {
}
