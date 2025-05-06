package org.maciooo.domain.drawdate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
@Configuration
class DrawDateConfiguration {
    @Bean
    Clock clock () {
        return Clock.systemDefaultZone();
    }
}
