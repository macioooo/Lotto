package org.maciooo.domain.resultchecker;

import org.maciooo.domain.numberreceiver.dto.TicketDto;
import org.maciooo.domain.resultchecker.dto.PlayerDto;

import java.util.List;

class ResultCheckerMapper {
    static Ticket mapToTicket(TicketDto ticketDto) {
        return Ticket.builder()
                .ticketId(ticketDto.ticketId())
                .numbersFromUser(ticketDto.numbersFromUser())
                .drawDate(ticketDto.drawDate())
                .build();
    }
    static TicketDto mapToTicketDto(Ticket ticket) {
        return TicketDto.builder()
                .drawDate(ticket.drawDate())
                .ticketId(ticket.ticketId())
                .numbersFromUser(ticket.numbersFromUser())
                .build();
    }
    static List<Ticket> mapToTicketList(List<TicketDto> ticketDtos) {
        return ticketDtos.stream()
                .map(ResultCheckerMapper::mapToTicket)
                .toList();
    }

    static List<TicketDto> mapToTicketDtoList(List<Ticket> tickets) {
        return tickets.stream().map(ResultCheckerMapper::mapToTicketDto).toList();
    }

    static List<PlayerDto> mapPlayersToResult(List<Player> players) {
        return players.stream()
                .map(player -> PlayerDto
                        .builder()
                        .playerNumbers(player.playerNumbers())
                        .guessedNumbers(player.guessedNumbers())
                        .isWinner(player.isWinner())
                        .ticketId(player.ticketId())
                        .drawDate(player.drawDate())
                        .build()).toList();
    }
}
