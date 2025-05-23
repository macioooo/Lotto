package org.maciooo.domain.numbergenerator.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record WinningNumbersDto(Set<Integer> winningNumbers, String drawDate) {

}
