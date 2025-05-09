package org.maciooo;

import org.maciooo.domain.numbergenerator.NumberGeneratorFacadeConfigProperties;
import org.maciooo.infrastracture.numbergenerator.http.RandomNumberGeneratorRestTemplateConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({NumberGeneratorFacadeConfigProperties.class, RandomNumberGeneratorRestTemplateConfigProperties.class})
@EnableScheduling
@EnableMongoRepositories
public class LottoSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(LottoSpringBootApplication.class, args);
    }

}


