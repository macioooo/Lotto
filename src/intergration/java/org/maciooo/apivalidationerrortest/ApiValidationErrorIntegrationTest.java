package org.maciooo.apivalidationerrortest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.maciooo.BaseIntegrationTest;
import org.maciooo.infrastracture.apivalidation.ApiValidationErrorsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationErrorIntegrationTest extends BaseIntegrationTest {
    private final static String ENDPOINT = "/inputNumbers";

    @Test
    public void inputNumbers_whenNoNumbersProvided_shouldReturnBadRequest() throws Exception {

        // when

        ResultActions preformInputNumbersWithoutNumbers = mockMvc.perform(post(ENDPOINT).content("""
                {
                    "numbersFromUser": []
                }
                """.trim()).contentType(MediaType.APPLICATION_JSON));
        MvcResult result = preformInputNumbersWithoutNumbers.andExpect(status().isBadRequest()).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ApiValidationErrorsDto errorsDto = objectMapper.readValue(jsonResponse, ApiValidationErrorsDto.class);
        Assertions.assertThat(errorsDto.errorMessages()).containsExactlyInAnyOrder(
                "inputNumbers must not be empty"
        );
        assertEquals(errorsDto.status(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void inputNumbers_whenRequestIsEmpty_shouldReturnBadRequest() throws Exception {

        // when
        ResultActions performInputNumbersWithoutNumbers = mockMvc.perform(post(ENDPOINT).content("""
                {}        
                """.trim()).contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult result = performInputNumbersWithoutNumbers.andExpect(status().isBadRequest()).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ApiValidationErrorsDto errorsDto = objectMapper.readValue(jsonResponse, ApiValidationErrorsDto.class);
        Assertions.assertThat(errorsDto.errorMessages()).containsExactlyInAnyOrder(
                "inputNumbers must not be empty",
                "inputNumbers must not be null"
        );
        assertEquals(errorsDto.status(), HttpStatus.BAD_REQUEST);

    }

}
