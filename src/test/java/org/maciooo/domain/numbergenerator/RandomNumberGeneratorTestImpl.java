package org.maciooo.domain.numbergenerator;

import java.util.Set;

class RandomNumberGeneratorTestImpl implements RandomNumberGenerable {

    private final Set<Integer> generatedNumbers;
    public RandomNumberGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1,2,3,4,5,6);
    }

    RandomNumberGeneratorTestImpl(Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    @Override
    public SixRandomGeneratedNumbers generateWinningNumbers() {
        return SixRandomGeneratedNumbers.builder()
                .numbers(this.generatedNumbers)
                .build();
    }
}
