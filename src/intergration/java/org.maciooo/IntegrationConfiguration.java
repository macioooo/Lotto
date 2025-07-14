package org.maciooo;

import org.maciooo.domain.AdjustableClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Configuration
@Profile("integration")
public class IntegrationConfiguration {
    @Bean
    @Primary
    AdjustableClock clock() {
        return AdjustableClock.ofLocalDateAndLocalTime(LocalDate.of(2025,5,9), LocalTime.of(10, 00), ZoneId.systemDefault());
    }
}
