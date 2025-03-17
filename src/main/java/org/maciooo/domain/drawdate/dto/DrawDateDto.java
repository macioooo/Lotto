package org.maciooo.domain.drawdate.dto;

import lombok.Builder;

@Builder
public record DrawDateDto(String date, String message) {
}
