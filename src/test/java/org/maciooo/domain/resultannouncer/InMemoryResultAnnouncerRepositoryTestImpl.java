package org.maciooo.domain.resultannouncer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryResultAnnouncerRepositoryTestImpl implements ResultAnnouncerRepository{

    Map<String, ResultResponse> results = new ConcurrentHashMap<>();
    @Override
    public ResultResponse findByTicketId(String ticketId) {
        return results.get(ticketId);
    }

    @Override
    public ResultResponse save(ResultResponse result) {
        results.put(result.ticketId(), result);
        return result;
    }

    @Override
    public boolean existById(String ticketId) {
        return results.containsKey(ticketId);
    }
}
