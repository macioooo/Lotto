package org.maciooo.domain.numbergenerator;

import lombok.Builder;

import java.util.Set;
@Builder
record SixRandomGeneratedNumbers(Set<Integer> numbers) {
}
