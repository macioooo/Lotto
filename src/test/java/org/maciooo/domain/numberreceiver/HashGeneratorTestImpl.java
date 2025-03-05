package org.maciooo.domain.numberreceiver;

public class HashGeneratorTestImpl implements HashGenerable {
    private final String ticketId;
    HashGeneratorTestImpl(String ticketId) {
        this.ticketId = ticketId;
    }

    public HashGeneratorTestImpl() {
        this.ticketId = "1234";
    }

    @Override
    public String getHash() {
        return ticketId;
    }
}
