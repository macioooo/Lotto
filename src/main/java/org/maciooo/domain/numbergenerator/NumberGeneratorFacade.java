package org.maciooo.domain.numbergenerator;


import lombok.AllArgsConstructor;
import org.maciooo.domain.drawdate.DrawDateFacade;
import org.maciooo.domain.drawdate.dto.DrawDateDto;
import org.maciooo.domain.numbergenerator.dto.SixRandomGeneratedNumbersDto;
import org.maciooo.domain.numbergenerator.dto.WinningNumbersDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class NumberGeneratorFacade {
    private final RandomNumberGenerable numberGenerator;
    private final DrawDateFacade drawDateFacade;
    private final NumberGeneratorValidator numberGeneratorValidator;
    private final NumberGeneratorRepository numbersRepository;
    private final NumberGeneratorFacadeConfigProperties properties;

    public WinningNumbersDto generateWinningNumbers() {
        DrawDateDto drawDateDto = drawDateFacade.getNextDrawDate();
        DrawDate drawDate = WinningNumbersMapper.mapFromDrawDateDto(drawDateDto);
        if (!getWinningNumbersByDrawDate(drawDate.date()).winningNumbers().isEmpty()) {
            throw new WinningNumbersAlreadyGenerated("Numbers were already generated for this week!");
        }
        SixRandomGeneratedNumbersDto sixNumbersDto = numberGenerator.generateWinningNumbers(properties.count(), properties.lowerBand(), properties.upperBand());
        Set<Integer> generatedNumbers = sixNumbersDto.numbers();
        numberGeneratorValidator.validateWinningNumbers(generatedNumbers);
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .generatedWinningNumbers(generatedNumbers)
                .drawDate(drawDate.date())
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

    public WinningNumbersDto getWinningNumbersByDrawDate(String drawDate) {
        WinningNumbers winningNumbers = numbersRepository.findWinningNumbersByDrawDate(drawDate);
        return Optional.ofNullable(winningNumbers)
                .map(WinningNumbersMapper::mapFromWinningNumbersToDto)
                .orElse(new WinningNumbersDto(Collections.emptySet(), drawDate));
    }

}
