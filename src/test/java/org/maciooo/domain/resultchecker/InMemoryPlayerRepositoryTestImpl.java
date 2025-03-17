package org.maciooo.domain.resultchecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryPlayerRepositoryTestImpl implements PlayerRepository{

    private Map<String, Player> playerMap = new ConcurrentHashMap<>();
    @Override
    public List<Player> saveAll(List<Player> players) {
        playerMap = players.stream()
                .collect(Collectors.toConcurrentMap(Player::ticketId, player -> player));
        return players;
    }

    @Override
    public Optional<Player> findPlayerByTicketId(String ticketId) {
        return Optional.ofNullable(playerMap.get(ticketId));
    }
}
