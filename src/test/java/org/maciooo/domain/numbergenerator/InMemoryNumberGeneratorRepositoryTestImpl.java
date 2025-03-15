package org.maciooo.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryNumberGeneratorRepositoryTestImpl implements NumberGeneratorRepository {

    private final Map<LocalDateTime, WinningNumbers> winningNumbersMap = new ConcurrentHashMap<>();
    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        return winningNumbersMap.put(winningNumbers.drawDate(), winningNumbers);
    }

    @Override
    public List<WinningNumbers> findAllWinningNumbers() {
        return winningNumbersMap
                .values()
                .stream()
                .toList();
    }

    @Override
    public WinningNumbers findWinningNumbersByDrawDate(LocalDateTime drawDate) {
        return winningNumbersMap.get(drawDate);
    }
}
