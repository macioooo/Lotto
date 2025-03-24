package org.maciooo.domain.resultannouncer;

interface ResultAnnouncerRepository {
    ResultResponse findByTicketId(String ticketId);

    ResultResponse save(ResultResponse result);

    boolean existById(String ticketId);
}
