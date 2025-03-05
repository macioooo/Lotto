    package org.maciooo.domain.numberreceiver;

    import lombok.AllArgsConstructor;
    import org.maciooo.domain.drawdate.DrawDateFacade;
    import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
    import org.maciooo.domain.numberreceiver.dto.TicketDto;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Set;

    @AllArgsConstructor
    public class NumberReceiverFacade {
        private final NumberValidator numberValidator;
        private final NumberReceiverRepository repository;
        private final DrawDateFacade drawDateFacade;
        private final HashGenerable hashGenerator;

        public InputNumberResultDto inputNumbers(Set<Integer> numbersGivenByUser) {
            if (!numberValidator.validate(numbersGivenByUser).isEmpty()) {
                String resultMessage = numberValidator.createResultMessage();
                return InputNumberResultDto
                        .builder()
                        .message(resultMessage)
                        .build();
            }
                String ticketId = hashGenerator.getHash();
                LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
                Ticket ticket = Ticket
                        .builder()
                        .ticketId(ticketId)
                        .drawDate(drawDate)
                        .numbersGivenByUser(numbersGivenByUser)
                        .build();
                repository.save(ticket);
                TicketDto ticketDto = TicketDto
                        .builder()
                        .ticketId(ticket.ticketId())
                        .drawDate(ticket.drawDate())
                        .numbersFromUser(ticket.numbersGivenByUser())
                        .build();
                return InputNumberResultDto
                        .builder()
                        .message(ValidationMessages.SUCCESS.message)
                        .ticketDto(ticketDto)
                        .build();
            }

        public List<TicketDto> getAllTicketsForNextDrawDate() {
            return getAllTicketsByDrawDate(drawDateFacade.getNextDrawDate());
        }

        public List<TicketDto> getAllTicketsByDrawDate(LocalDateTime date) {
            if (date.isAfter(drawDateFacade.getNextDrawDate())) {
                throw new DrawDateException("You can't check the tickets for future draws.");
            }
            List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
            return allTicketsByDrawDate.stream()
                    .map(TicketMapper::mapFromTicket)
                    .toList();
        }

        public TicketDto getTicketById(String ticketId) {
            Ticket ticket = repository.findByTicketId(ticketId);
            return TicketDto
                    .builder()
                    .ticketId(ticket.ticketId())
                    .numbersFromUser(ticket.numbersGivenByUser())
                    .drawDate(ticket.drawDate())
                    .build();


        }



    }
