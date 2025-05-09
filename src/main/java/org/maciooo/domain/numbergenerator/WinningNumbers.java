package org.maciooo.domain.numbergenerator;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Builder
@Document
record WinningNumbers(@Id String id,
                      Set<Integer> generatedWinningNumbers,
                      String drawDate) {

}
