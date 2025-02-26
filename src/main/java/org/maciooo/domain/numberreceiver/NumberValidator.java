package org.maciooo.domain.numberreceiver;

import java.util.Set;

class NumberValidator {

    private static final int MIN_NUMBER_FROM_USER = 1;
    private static final int MAX_NUMBER_FROM_USER = 99;
    private static final int AMOUNT_OF_NUMBERS = 6;

    boolean areAllNumbersInRange(Set<Integer> numbersGivenByUser) {
        return numbersGivenByUser.stream()
                .filter(number -> number >= MIN_NUMBER_FROM_USER)
                .filter(number -> number <= MAX_NUMBER_FROM_USER)
                .count() == AMOUNT_OF_NUMBERS;
    }
}
