package org.maciooo.domain.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record WinningNumbers(Set<Integer> generatedWinningNumbers, LocalDateTime drawDate) {

}
