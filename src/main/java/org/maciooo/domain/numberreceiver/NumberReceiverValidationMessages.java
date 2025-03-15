package org.maciooo.domain.numberreceiver;

enum NumberReceiverValidationMessages {
    LESS_OR_MORE_THAN_6_NUMBERS_GIVEN("YOU SHOULD GIVE 6 UNIQUE NUMBERS"),
    NOT_IN_RANGE("YOU SHOULD GIVE NUMBERS BETWEEN 1 AND 99"),
    SUCCESS("SUCCESS");
    final String message;
    NumberReceiverValidationMessages(String message) {
        this.message = message;
    }
}
