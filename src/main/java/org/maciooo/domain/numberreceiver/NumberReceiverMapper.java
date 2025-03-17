package org.maciooo.domain.numberreceiver;

import org.maciooo.domain.drawdate.dto.DrawDateDto;
import org.maciooo.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;

class NumberReceiverMapper {
    static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .numbersFromUser(ticket.numbersGivenByUser())
                .drawDate(ticket.drawDate())
                .ticketId(ticket.ticketId()).build();
    }

    static DrawDate mapFromDrawDateDto(DrawDateDto drawDateDto) {
        return DrawDate.builder()
                .date(drawDateDto.date())
                .message(drawDateDto.message())
                .build();
    }

    static LocalDateTime mapFromStringToLocalDateTime(String date) {
        return LocalDateTime.parse(date);
    }

}
