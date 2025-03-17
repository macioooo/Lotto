package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.drawdate.dto.DrawDateDto;
import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;

class WinningNumbersMapper {
    static WinningNumbersDto mapFromWinningNumbersToDto(WinningNumbers winningNumbers) {
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers.generatedWinningNumbers())
                .drawDate(winningNumbers.drawDate())
                .build();
    }

    static DrawDate mapFromDrawDateDto(DrawDateDto drawDateDto) {
        return DrawDate.builder()
                .date(drawDateDto.date())
                .message(drawDateDto.message())
                .build();


    }
}
