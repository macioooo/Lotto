package org.maciooo.domain.resultannouncer.dto;

import lombok.Builder;

@Builder
public record ResultAnnouncerResponseDto(ResultResponseDto responseDto, String message) {
}
