package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.numbergenerator.dto.SixRandomGeneratedNumbersDto;

public interface RandomNumberGenerable {
    SixRandomGeneratedNumbersDto generateWinningNumbers(int count, int lowerBand, int upperBand);

}
