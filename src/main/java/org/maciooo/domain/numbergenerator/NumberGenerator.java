package org.maciooo.domain.numbergenerator;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class NumberGenerator implements NumberGenerable{
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 99;
    private static final int BOUND = (MAX_NUM - MIN_NUM) + 1;

    @Override
    public Set<Integer> generateWinningNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (isAmountOfGeneratedNumbersLowerThanSix(winningNumbers)) {
            winningNumbers.add(generateRandomNumber());
        }
        return winningNumbers;
    }
    private boolean isAmountOfGeneratedNumbersLowerThanSix (Set<Integer> numbers) {
        return numbers.size()<6;
    }
    private int generateRandomNumber() {
        Random random = new SecureRandom();
        return random.nextInt(BOUND)+MIN_NUM;
    }
}
