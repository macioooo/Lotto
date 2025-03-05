package org.maciooo.domain.numberreceiver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator {

    private static final int MIN_NUMBER_FROM_USER = 1;
    private static final int MAX_NUMBER_FROM_USER = 99;
    private static final int AMOUNT_OF_NUMBERS = 6;

    List<ValidationMessages> messages = new LinkedList<>();

    List<ValidationMessages> validate(Set<Integer> numbersGivenByUser) {
        if (!amountOfNumbersEqualsSix(numbersGivenByUser)) {
            messages.add(ValidationMessages.LESS_OR_MORE_THAN_6_NUMBERS_GIVEN);
        }
        if (!numbersAreInRangeFrom1To99(numbersGivenByUser)) {
            messages.add(ValidationMessages.NOT_IN_RANGE);
        }
        return messages;
    }
    String createResultMessage() {
        return this.messages
                .stream()
                .map(validationMessages -> validationMessages.message)
                .collect(Collectors.joining(", "));
    }

    private boolean amountOfNumbersEqualsSix(Set<Integer> numbersGivenByUser) {
        return numbersGivenByUser.size() == AMOUNT_OF_NUMBERS;
    }

    private boolean numbersAreInRangeFrom1To99(Set<Integer> numbersGivenByUser) {
        return numbersGivenByUser.stream().allMatch(number -> number <= MAX_NUMBER_FROM_USER && number >= MIN_NUMBER_FROM_USER);
    }
}
