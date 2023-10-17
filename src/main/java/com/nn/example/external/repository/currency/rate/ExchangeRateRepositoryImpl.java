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

@RequiredArgsConstructor
@Service
class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

    private final RestTemplate restTemplate;

    @Value("${nbp.api.url}")
    private String apiUrl;

    @Override
    public BigDecimal getExchangeRate() {
        try {
            NbpResponse nbpResponse = restTemplate.getForObject(apiUrl, NbpResponse.class);
            if (nbpResponse != null && nbpResponse.getRates() != null && nbpResponse.getRates().size() > 0) {
                return nbpResponse.getRates().get(0).getMid();
            } else {
                throw new NbpApiRequestException("Unable to fetch exchange rate for USD");
            }
        } catch (RestClientException e) {
            throw new NbpApiRequestException("Error while making a request to the NBP API");
        }
    }
}
