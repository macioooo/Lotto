package org.maciooo.infrastracture.numbergenerator.http;


import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.http.client.config")
@Builder
public record RandomNumberGeneratorRestTemplateConfigProperties(int port, String uri, long connectionTimeout, long readTimeout) {
}
