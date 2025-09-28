package org.maciooo.domain.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
interface PlayerRepository extends MongoRepository<Player, String> {
    <S extends Player> List<S> saveAll(Iterable<S> entities);
    Optional<Player> findPlayerByTicketId(String ticketId);

}
