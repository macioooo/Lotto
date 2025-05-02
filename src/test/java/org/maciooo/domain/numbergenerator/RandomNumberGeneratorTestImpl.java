package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.numbergenerator.dto.SixRandomGeneratedNumbersDto;

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
    public SixRandomGeneratedNumbersDto generateWinningNumbers(int count, int lowerBand, int upperBand) {
        return SixRandomGeneratedNumbersDto.builder()
                .numbers(this.generatedNumbers)
                .build();
    }
}
