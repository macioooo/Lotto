package org.maciooo.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.List;

interface NumberGeneratorRepository {
    WinningNumbers save(WinningNumbers winningNumbers);

    List<WinningNumbers> findAllWinningNumbers();
    WinningNumbers findWinningNumbersByDrawDate(LocalDateTime drawDate);
}
