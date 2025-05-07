package org.maciooo.domain.numberreceiver;

import org.maciooo.domain.drawdate.DrawDateFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.List;

@Configuration
class NumberReceiverConfiguration {
    @Bean
    HashGenerable hashGenerable() {return new HashGenerator();}

    @Bean
    NumberReceiverRepository numberReceiverRepository() {return new NumberReceiverRepository() {
        @Override
        public Ticket save(Ticket ticket) {
            return null;
        }

        @Override
        public List<Ticket> findAllTicketsByDrawDate(String date) {
            return null;
        }

        @Override
        public Ticket findByTicketId(String ticketId) {
            return null;
        }
    };}
    @Bean
    NumberReceiverFacade numberReceiverFacade(HashGenerable hashGenerator, Clock clock, NumberReceiverRepository repository) {
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        NumberReceiverValidator numberValidator = new NumberReceiverValidator();
        return new NumberReceiverFacade(numberValidator, repository, drawDateFacade, hashGenerator);
    }

}
