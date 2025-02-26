package org.maciooo.domain.numberreceiver;

import org.maciooo.domain.numberreceiver.dto.TicketDto;

class TicketMapper {
    static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .numbersFromUser(ticket.numbersGivenByUser())
                .drawDate(ticket.drawDate())
                .ticketId(ticket.ticketId()).build();
    }

}
