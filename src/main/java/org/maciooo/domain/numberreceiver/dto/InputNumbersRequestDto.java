package org.maciooo.domain.numberreceiver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record InputNumbersRequestDto(@NotEmpty(message = "{inputNumbers.not.empty}")
                              @NotNull(message = "{inputNumbers.not.null}")
                              Set<Integer> numbersFromUser) {
}
