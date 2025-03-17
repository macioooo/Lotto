package org.maciooo.domain.numberreceiver;

import java.util.List;

interface NumberReceiverRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketsByDrawDate(String date);

    Ticket findByTicketId(String ticketId);
}
