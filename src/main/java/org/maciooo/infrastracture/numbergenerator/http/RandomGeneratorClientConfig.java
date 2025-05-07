package org.maciooo.infrastracture.numbergenerator.http;

import org.maciooo.domain.numbergenerator.RandomNumberGenerable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RandomGeneratorClientConfig {
    private final RandomNumberGeneratorRestTemplateConfigProperties properties;

    public RandomGeneratorClientConfig(RandomNumberGeneratorRestTemplateConfigProperties properties) {
        this.properties = properties;
    }
    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandle) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandle)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    @Bean
    public RandomNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate) {
        return new RandomNumberGeneratorRestTemplate(restTemplate, properties.uri(), properties.port());
    }


}
