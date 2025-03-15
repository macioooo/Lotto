package org.maciooo.domain.numbergenerator;

class WinningNumbersAlreadyGenerated extends IllegalStateException{
    public WinningNumbersAlreadyGenerated(String message) {
        super(message);
    }
}
