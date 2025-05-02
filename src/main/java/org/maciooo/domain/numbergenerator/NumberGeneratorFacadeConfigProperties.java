package org.maciooo.domain.numbergenerator;


import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.facade")
@Builder
public record NumberGeneratorFacadeConfigProperties(int count, int lowerBand, int upperBand) {
}
