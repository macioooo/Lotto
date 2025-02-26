package org.maciooo.domain.numberreceiver;

import lombok.AllArgsConstructor;
import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
import org.maciooo.domain.numberreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class NumberReceiverFacade {
    private final NumberValidator numberValidator;
    private final NumberReceiverRepository repository;
    private final Clock clock;

    public InputNumberResultDto inputNumbers(Set<Integer> numbersGivenByUser) {
        if (numberValidator.areAllNumbersInRange(numbersGivenByUser)) {
            String ticketId = UUID.randomUUID().toString();
            LocalDateTime drawDate = LocalDateTime.now(clock);
            Ticket savedTicket = repository.save(new Ticket(ticketId, drawDate, numbersGivenByUser));
            return InputNumberResultDto
                    .builder()
                    .drawDate(savedTicket.drawDate())
                    .ticketId(savedTicket.ticketId())
                    .numbersGivenByUser(savedTicket.numbersGivenByUser())
                    .message("success")
                    .build();
        }
        return InputNumberResultDto
                .builder()
                .message("failed")
                .build();
    }

    public List<TicketDto> userNumbers(LocalDateTime date) {
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }
}
