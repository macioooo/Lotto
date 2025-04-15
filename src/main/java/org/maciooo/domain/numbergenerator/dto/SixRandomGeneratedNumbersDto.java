package org.maciooo.domain.numbergenerator.dto;

import lombok.Builder;

import java.util.Set;
@Builder
public record SixRandomGeneratedNumbersDto(Set<Integer> numbers) {
}
