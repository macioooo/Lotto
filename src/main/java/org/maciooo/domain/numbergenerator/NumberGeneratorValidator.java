package org.maciooo.domain.numbergenerator;

import java.util.Set;

class NumberGeneratorValidator {
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 99;

    Set<Integer> validateWinningNumbers(Set<Integer> generatedWinningNumbers) {
        if (areGeneratedWinningNumbersOutOfRange(generatedWinningNumbers)) {
            throw new WinningNumbersOutOfRange("Numbers are out of range 1-99");
        }
        return generatedWinningNumbers;
    }

    private boolean areGeneratedWinningNumbersOutOfRange(Set<Integer> generatedWinningNumbers) {
        return generatedWinningNumbers.stream()
                .anyMatch(number -> number < MIN_NUM || number > MAX_NUM);
    }
}
