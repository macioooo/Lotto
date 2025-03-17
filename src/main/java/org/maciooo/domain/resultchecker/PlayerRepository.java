package org.maciooo.domain.resultchecker;

import java.util.List;
import java.util.Optional;

interface PlayerRepository {
    List<Player> saveAll(List<Player> player);
    Optional<Player> findPlayerByTicketId(String ticketId);

}
