package com.nn.example.delivery.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CurrencyExchangeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Sql(scripts = "/createUserWithTwoAccounts.sql")
    @Test
    void shouldExchangeCurrency() throws Exception {
        //given
        ExchangeCurrencyRequest request = ExchangeCurrencyRequest
                .builder()
                .targetCurrency("USD")
                .targetIdAccountNumber("d040802-fa1d-4b18-a964-c6cdb7ac7531")
                .sourceIdAccountNumber("aaf3fb98-b40d-4cc7-adfa-3a6ccec17268")
                .amount(BigDecimal.valueOf(100))
                .accountId(1L)
                .build();

        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bank-accounts/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        ExchangeCurrencyResponse response = objectMapper.readValue(content, ExchangeCurrencyResponse.class);
        assertEquals("aaf3fb98-b40d-4cc7-adfa-3a6ccec17268", response.getSourceIdAccountNumber());
        assertEquals("d040802-fa1d-4b18-a964-c6cdb7ac7531", response.getTargetIdAccountNumber());
        assertEquals("USD", response.getTargetCurrency());
        assertEquals(BigDecimal.valueOf(100), response.getAmount());
    }
}
