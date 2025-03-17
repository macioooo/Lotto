    package org.maciooo.domain.numberreceiver;

    import lombok.AllArgsConstructor;
    import org.maciooo.domain.drawdate.DrawDateFacade;
    import org.maciooo.domain.drawdate.dto.DrawDateDto;
    import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
    import org.maciooo.domain.numberreceiver.dto.TicketDto;
    import java.time.LocalDateTime;
    import java.util.Collections;
    import java.util.List;
    import java.util.Set;

    @AllArgsConstructor
    public class NumberReceiverFacade {
        private final NumberReceiverValidator numberValidator;
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
                DrawDateDto drawDateDto = drawDateFacade.getNextDrawDate();
                DrawDate drawDate = NumberReceiverMapper.mapFromDrawDateDto(drawDateDto);
                Ticket ticket = Ticket
                        .builder()
                        .ticketId(ticketId)
                        .drawDate(drawDate.date())
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
                        .message(NumberReceiverValidationMessages.SUCCESS.message)
                        .ticketDto(ticketDto)
                        .build();
            }

        public List<TicketDto> getAllTicketsForNextDrawDate() {
            DrawDate drawDate = NumberReceiverMapper.mapFromDrawDateDto(drawDateFacade.getNextDrawDate());
            return getAllTicketsByDrawDate(drawDate.date());
        }

        public List<TicketDto> getAllTicketsByDrawDate(String date) {
            LocalDateTime dateTime = NumberReceiverMapper.mapFromStringToLocalDateTime(date);
            LocalDateTime nextDrawDate =NumberReceiverMapper.mapFromStringToLocalDateTime(drawDateFacade.getNextDrawDate().date());
            if (dateTime.isAfter(nextDrawDate)) {
                return Collections.EMPTY_LIST;
            }
            List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
            return allTicketsByDrawDate.stream()
                    .map(NumberReceiverMapper::mapFromTicket)
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
