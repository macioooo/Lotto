package org.maciooo.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.maciooo.BaseIntegrationTest;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numbergenerator.RandomNumberGenerable;
import org.maciooo.domain.numbergenerator.WinningNumbersNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.awaitility.Awaitility.await;

public class UserPlayedAndWonIntegrationTest extends BaseIntegrationTest {
    @Autowired
    RandomNumberGenerable randomNumberGenerable;

    @Autowired
    NumberGeneratorFacade facade;

    @Test
    public void should_user_win_and_system_should_generate_winners() {
        // step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=6")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[1,2,3,4,5,6]")
                ));
        // step 2: system fetched winning numbers for draw date 10.05.2025
        String drawDate = LocalDateTime.of(2025, 5, 10, 12, 0, 0).toString();
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                            try {
                                return !facade.getWinningNumbersByDrawDate(drawDate).winningNumbers().isEmpty();
                            } catch (WinningNumbersNotFoundException exception) {
                                return false;
                            }
                        }
                );
    }
}
