package org.maciooo.domain.numberreceiver.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record TicketDto(String ticketId, String drawDate, Set<Integer> numbersFromUser) {
}
