package org.maciooo.domain.numberreceiver;

import lombok.Builder;

import java.util.Set;

@Builder
record Ticket(String ticketId, String drawDate, Set<Integer> numbersGivenByUser) {
}
