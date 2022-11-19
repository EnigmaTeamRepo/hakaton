package com.enigma.hakaton.service;

import com.enigma.hakaton.model.DailyJsonResponse;
import com.enigma.hakaton.model.DailyJsonResponse.Valute;
import com.enigma.hakaton.model.enums.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@EnableScheduling
public class ValuteService {

    private final Logger log = LoggerFactory.getLogger("com.enigma.hakaton.service");

    private final Map<Currency, Valute> valuteMap = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;

    @Autowired
    public ValuteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.updateValuteData();
    }

    public Long getAmount(Currency valuteCurrency, Long lots) {
        if (Currency.RUB.equals(valuteCurrency)) {
            throw new RuntimeException();
        }
        BigDecimal perLot = new BigDecimal(valuteMap.get(valuteCurrency).getValue());
        return perLot
                .multiply(new BigDecimal(lots))
                .multiply(new BigDecimal("100"))
                .divide(new BigDecimal("1"), 2, RoundingMode.HALF_UP)
                .longValue();
    }

    @Scheduled(cron = "${name-of-the-cron:0 0/30 * * * ?}")
    public void updateValuteData() {
        ResponseEntity<DailyJsonResponse> forEntity =
                restTemplate.getForEntity("https://www.cbr-xml-daily.ru/daily_json.js", DailyJsonResponse.class);
        Map<String, Valute> valute = Objects.requireNonNull(forEntity.getBody()).getValute();
        valuteMap.put(Currency.USD, valute.get(Currency.USD.name()));
        valuteMap.put(Currency.EUR, valute.get(Currency.EUR.name()));
    }
}
