package org.maciooo.domain.numbergenerator;


import lombok.AllArgsConstructor;
import org.maciooo.domain.drawdate.DrawDateFacade;
import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class NumberGeneratorFacade {
    private final NumberGenerable numberGenerator;
    private final DrawDateFacade drawDateFacade;
    private final NumberGeneratorValidator numberGeneratorValidator;
    private final NumberGeneratorRepository numbersRepository;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        if (!getWinningNumbersByDrawDate(drawDate).winningNumbers().isEmpty()) {
            throw new WinningNumbersAlreadyGenerated("Numbers were already generated for this week!");
        }
        Set<Integer> generatedNumbers = numberGenerator.generateWinningNumbers();
        numberGeneratorValidator.validateWinningNumbers(generatedNumbers);
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .generatedWinningNumbers(generatedNumbers)
                .drawDate(drawDate)
                .build();
        numbersRepository.save(winningNumbers);
        return WinningNumbersMapper.mapFromWinningNumbersToDto(winningNumbers);
    }

    public List<WinningNumbersDto> getAllWinningNumbers() {
        return numbersRepository.findAllWinningNumbers()
                .stream()
                .map(WinningNumbersMapper::mapFromWinningNumbersToDto)
                .toList();
    }

    public WinningNumbersDto getWinningNumbersByDrawDate(LocalDateTime drawDate) {
        WinningNumbers winningNumbers = numbersRepository.findWinningNumbersByDrawDate(drawDate);
        return Optional.ofNullable(winningNumbers)
                .map(WinningNumbersMapper::mapFromWinningNumbersToDto)
                .orElse(new WinningNumbersDto(Collections.emptySet(), drawDate));
    }

}
