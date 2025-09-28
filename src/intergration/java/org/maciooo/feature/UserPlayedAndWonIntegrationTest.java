package org.maciooo.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.maciooo.BaseIntegrationTest;
import org.maciooo.domain.numbergenerator.NumberGeneratorFacade;
import org.maciooo.domain.numbergenerator.RandomNumberGenerable;
import org.maciooo.domain.numbergenerator.WinningNumbersNotFoundException;
import org.maciooo.domain.numberreceiver.dto.InputNumberResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayedAndWonIntegrationTest extends BaseIntegrationTest {
    @Autowired
    RandomNumberGenerable randomNumberGenerable;

    @Autowired
    NumberGeneratorFacade facade;

    @Test
    public void should_user_win_and_system_should_generate_winners() throws Exception {
        // step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=6")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[1,2,3,4,5,6]")
                ));
        // step 2: system fetched winning numbers for draw date 10.05.2025
        //  given
        String drawDate = LocalDateTime.of(2025, 5, 10, 12, 0, 0).toString();
        //when&then

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
        // step 3: user made POST /inputnumbers with 6 numbers [1,2,3,4,5,6] at 8.05.2025 7:35 and system returned OK (200) with message "success" and Ticket Dto

        //given
        //when
        ResultActions perform = mockMvc.perform(post(
                "/inputNumbers"
        ).content("""
                {
                    "numbersFromUser":[1,2,3,4,5,6]
                }
                """).contentType(MediaType.APPLICATION_JSON));
        final var mvcResult = perform.andExpect(status().isOk()).andReturn();
        final var jsonResult = mvcResult.getResponse().getContentAsString();
        InputNumberResultDto resultDto = objectMapper.readValue(jsonResult, InputNumberResultDto.class);
        //then
        assertAll(
                () -> assertThat(resultDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(resultDto.message().equals("SUCCESS")),
                () -> assertNotNull(resultDto.ticketDto().ticketId()));

        // step 4: user made GET /results/nonExistingId and system returned 404(NOT_FOUND) and body with (message: Not found for id: nonExistingId and status: NOT_FOUND
        //given
        //when
        ResultActions performGetWithNonExistingId = mockMvc.perform(get("/results/" + "nonExistingId"));
        //then
        performGetWithNonExistingId.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "message": "Not found for id: nonExistingId",
                            "status": "NOT_FOUND"
                        }
                        """.trim()
                ));

    }
}
