package org.maciooo.domain.numbergenerator;

import lombok.AllArgsConstructor;
import org.maciooo.domain.numbergenerator.dto.OneRandomNumberFetcherResponseDto;

import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
class NumberGenerator implements NumberGenerable {
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 99;
    private final OneRandomNumberFetcher fetcher;

    @Override
    public Set<Integer> generateWinningNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (isAmountOfGeneratedNumbersLowerThanSix(winningNumbers)) {
            OneRandomNumberFetcherResponseDto fetcherResponseDto = fetcher.fetchOneRandomNumber(MIN_NUM, MAX_NUM);
            winningNumbers.add(fetcherResponseDto.number());
        }
        return winningNumbers;
    }

    private boolean isAmountOfGeneratedNumbersLowerThanSix(Set<Integer> numbers) {
        return numbers.size() < 6;
    }

}
