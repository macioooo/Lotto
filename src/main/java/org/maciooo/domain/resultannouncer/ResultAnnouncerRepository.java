package org.maciooo.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ResultAnnouncerRepository extends MongoRepository<ResultResponse, String> {
    ResultResponse findByTicketId(String ticketId);

    ResultResponse save(ResultResponse result);

    boolean existsByTicketId(String ticketId);

}
