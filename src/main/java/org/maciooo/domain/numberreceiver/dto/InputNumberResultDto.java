package org.maciooo.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record InputNumberResultDto(String message, TicketDto ticketDto) {

}
