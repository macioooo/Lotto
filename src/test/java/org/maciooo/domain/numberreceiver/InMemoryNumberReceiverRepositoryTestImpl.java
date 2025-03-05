package org.maciooo.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryNumberReceiverRepositoryTestImpl implements NumberReceiverRepository {

    Map<String, Ticket> inMemoryDataBase = new ConcurrentHashMap<>();
    @Override
    public Ticket save(Ticket ticket) {
        inMemoryDataBase.put(ticket.ticketId(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllTicketsByDrawDate(LocalDateTime date) {
        return inMemoryDataBase.values()
                .stream()
                .filter(ticket -> ticket.drawDate().isEqual(date))
                .toList();
    }

     @Override
     public Ticket findByTicketId(String ticketId) {
         return inMemoryDataBase.get(ticketId);
     }
 }
