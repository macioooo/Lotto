package org.maciooo.domain.numbergenerator;

import java.util.List;

interface NumberGeneratorRepository {
    WinningNumbers save(WinningNumbers winningNumbers);

    List<WinningNumbers> findAllWinningNumbers();
    WinningNumbers findWinningNumbersByDrawDate(String drawDate);
}
