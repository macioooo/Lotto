package org.maciooo.domain.numbergenerator;

import org.maciooo.domain.numbergenerator.dto.OneRandomNumberFetcherResponseDto;

interface OneRandomNumberFetcher {
    OneRandomNumberFetcherResponseDto fetchOneRandomNumber(int lowerBand, int upperBand);
}
