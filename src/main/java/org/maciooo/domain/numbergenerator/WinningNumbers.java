package org.maciooo.domain.numbergenerator;

import lombok.Builder;

import java.util.Set;

@Builder
record WinningNumbers(Set<Integer> generatedWinningNumbers, String drawDate) {

}
