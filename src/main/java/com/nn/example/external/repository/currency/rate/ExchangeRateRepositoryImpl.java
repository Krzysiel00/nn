package com.nn.example.external.repository.currency.rate;

import com.nn.example.core.exception.NbpApiRequestException;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

    private final RestTemplate restTemplate;

    @Value("${nbp.api.url}")
    private String apiUrl;

    @Override
    public BigDecimal getExchangeRate() {
        try {
            NbpResponse nbpResponse = Optional.ofNullable(restTemplate.getForObject(apiUrl, NbpResponse.class))
                    .orElseThrow(() -> new NbpApiRequestException("Unable to fetch exchange rate for USD"));

            return Optional.of(nbpResponse.getRates())
                    .filter(rates -> !rates.isEmpty())
                    .map(rates -> rates.get(0).getMid())
                    .orElseThrow(() -> new NbpApiRequestException("Unable to fetch exchange rate for USD"));

        } catch (RestClientException e) {
            throw new NbpApiRequestException("Error while making a request to the NBP API");
        }
    }
}
