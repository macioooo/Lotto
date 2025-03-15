package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;

class WinningNumbersMapper {
    static WinningNumbersDto mapFromWinningNumbersToDto(WinningNumbers winningNumbers) {
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers.generatedWinningNumbers())
                .drawDate(winningNumbers.drawDate())
                .build();
    }
}
