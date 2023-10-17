package com.nn.example.external.repository.currency.rate;

import com.nn.example.core.exception.NbpApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExchangeRateRepositoryTest {

    @MockBean
    private RestTemplate restTemplate;
    @Value("${nbp.api.url}")
    private String nbpApiUrl;

    @Autowired
    ExchangeRateRepositoryImpl exchangeRateRepository;

    @Test
    void getExchangeRate() {
        //given
        NbpResponse.Rate rate = new NbpResponse.Rate("200/A/NBP/2023", "2023-10-16", new BigDecimal("4.2505"));
        NbpResponse nbpResponse = new NbpResponse("A", "dolar amerykański", "USD", List.of(rate));
        when(restTemplate.getForObject(nbpApiUrl, NbpResponse.class)).thenReturn(nbpResponse);

        // when
        BigDecimal result = exchangeRateRepository.getExchangeRate();

        // then
        assertNotNull(result);
        assertEquals(new BigDecimal("4.2505"), result);
        verify(restTemplate, times(1)).getForObject(nbpApiUrl, NbpResponse.class);
    }

    @Test
    void getExchangeRateWithRestClientException() {
        // given
        when(restTemplate.getForObject(nbpApiUrl, NbpResponse.class))
                .thenThrow(new RestClientException("Mocked RestClientException"));

        // when-then
        assertThrows(NbpApiRequestException.class, () -> exchangeRateRepository.getExchangeRate());
    }
}
