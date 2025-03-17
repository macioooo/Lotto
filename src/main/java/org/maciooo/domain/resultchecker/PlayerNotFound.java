package org.maciooo.domain.resultchecker;

class PlayerNotFound extends RuntimeException{
    PlayerNotFound(String message) {
        super(message);
    }
}
