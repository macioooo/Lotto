package org.maciooo.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record AllPlayersDto(List<PlayerDto> results, String message) {
}
