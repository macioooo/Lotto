package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.numbergenerator.dto.OneRandomNumberFetcherResponseDto;

import java.security.SecureRandom;
import java.util.Random;

class OneSecureRandomNumberFetcher implements OneRandomNumberFetcher{
    @Override
    public OneRandomNumberFetcherResponseDto fetchOneRandomNumber(int lowerBand, int upperBand) {
        Random random = new SecureRandom();
        int generatedNumber = random.nextInt(upperBand-lowerBand + 1) + lowerBand;
        return OneRandomNumberFetcherResponseDto.builder()
                .number(generatedNumber)
                .build();
    }
}
