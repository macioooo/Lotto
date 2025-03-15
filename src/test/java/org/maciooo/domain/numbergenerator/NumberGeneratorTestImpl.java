package org.maciooo.domain.numbergenerator;

import java.util.Set;

class NumberGeneratorTestImpl implements NumberGenerable{

    private final Set<Integer> generatedNumbers;
    public NumberGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1,2,3,4,5,6);
    }

    NumberGeneratorTestImpl(Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    @Override
    public Set<Integer> generateWinningNumbers() {
        return this.generatedNumbers;
    }
}
