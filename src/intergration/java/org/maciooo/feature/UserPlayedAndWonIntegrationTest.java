package org.maciooo.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.maciooo.BaseIntegrationTest;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacadeConfigProperties;
import org.maciooo.domain.numbergenerator.RandomNumberGenerable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

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
        System.out.println(facade.generateWinningNumbers());
        //when
        //then
    }
}
