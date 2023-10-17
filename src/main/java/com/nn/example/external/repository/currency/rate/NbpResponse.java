package com.nn.example.external.repository.currency.rate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class NbpResponse {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Rate {
        private String no;
        private String effectiveDate;
        private BigDecimal mid;
    }
}

