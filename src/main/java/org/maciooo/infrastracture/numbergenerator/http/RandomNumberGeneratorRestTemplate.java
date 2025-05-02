package org.maciooo.infrastracture.numbergenerator.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.maciooo.domain.numbergenerator.RandomNumberGenerable;
import org.maciooo.domain.numbergenerator.dto.SixRandomGeneratedNumbersDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class RandomNumberGeneratorRestTemplate implements RandomNumberGenerable {

    private final static String SERVICE = "/api/v1.0/random";
    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public SixRandomGeneratedNumbersDto generateWinningNumbers(int count, int lowerBand, int upperBand) {
        log.info("Started generating numbers from external server");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            final ResponseEntity<List<Integer>> response = makeGetRequest(count, lowerBand, upperBand, requestEntity);
            Set<Integer> generatedNumbers = getSixUniqueNumbers(response);
            if (generatedNumbers.size() != 6) {
                log.error("Size of unique generated numbers is " + generatedNumbers.size());
                return generateWinningNumbers(count,lowerBand, upperBand);
            }
            return SixRandomGeneratedNumbersDto.builder()
                    .numbers(generatedNumbers).build();
        } catch (ResourceAccessException e) {
            log.error("Error occured while trying to generate winning numbers: " + e.getMessage());
            return SixRandomGeneratedNumbersDto.builder().build();
        }
    }
    private Set<Integer> getSixUniqueNumbers(ResponseEntity<List<Integer>> response) {
        List<Integer> responseBody = response.getBody();
        if (responseBody == null) {
            log.error("Response body was null. Returning empty collection");
            return Collections.emptySet();
        }
        log.info("success, returned response body: " + response);
        Set<Integer> uniqueNumbers = new HashSet<>(responseBody);
        return uniqueNumbers.stream().limit(6).collect(Collectors.toSet());
    }
    private ResponseEntity<List<Integer>> makeGetRequest(int count, int lowerBand, int upperBand, HttpEntity<HttpHeaders> requestEntity) {
        String url = UriComponentsBuilder.fromHttpUrl(getUrlForService(SERVICE))
                .queryParam("min", lowerBand)
                .queryParam("max", upperBand)
                .queryParam("count", count).toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return response;
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}

