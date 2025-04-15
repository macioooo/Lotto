package org.maciooo.infrastracture.numbergenerator.http;

import lombok.AllArgsConstructor;
import org.maciooo.domain.numbergenerator.RandomNumberGenerable;
import org.maciooo.domain.numbergenerator.dto.SixRandomGeneratedNumbersDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RandomNumberGeneratorRestTemplate implements RandomNumberGenerable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    private final static String SERVICE  = "/api/v1.0/random";


    @Override
    public SixRandomGeneratedNumbersDto generateWinningNumbers() {
        HttpHeaders headers = new HttpHeaders();
        String urlForService = getUrlForService(SERVICE);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .queryParam("min", 1)
                .queryParam("max", 99)
                .queryParam("count", 6).toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        List<Integer> numbers = response.getBody();
        return SixRandomGeneratedNumbersDto.builder()
                .numbers(numbers.stream().collect(Collectors.toSet()))
                .build();
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}

