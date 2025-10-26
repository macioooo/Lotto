package org.maciooo.infrastracture.apivalidation;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationErrorsDto(List<String> errorMessages,
                                     HttpStatus status) {

}
