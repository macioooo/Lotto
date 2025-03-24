package org.maciooo.domain.resultannouncer;

enum ResultAnnouncerMessages {
    YOU_WON_MESSAGE("Congratulations, you won!"),
    YOU_LOSE_MESSAGE("Sorry, you lost. Better luck next time!"),
    COULDNT_FIND_TICKET_MESSAGE("Sorry, we couldn't find the ticket with this ID. Check if it's correct and try again"),
    WAIT_FOR_RESULTS_MESSAGE("Results are being generated, please wait."),
    COOLDOWN_MESSAGE("You have to wait 5 minutes after you checked your ticket");

    final String message;
    ResultAnnouncerMessages(String message) {
        this.message = message;
    }
}
