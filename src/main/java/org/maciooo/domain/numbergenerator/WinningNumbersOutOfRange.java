package org.maciooo.domain.numbergenerator;

class WinningNumbersOutOfRange extends IllegalStateException{
    WinningNumbersOutOfRange(String message) {
        super(message);
    }
}
