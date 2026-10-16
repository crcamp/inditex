package com.gfx.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.gfx.domain.model.Price;
import com.gfx.infrastructure.controller.PriceResponseMapper;
import com.gfx.infrastructure.dto.PriceResponse;

class PriceResponseMapperTest {

    private final PriceResponseMapper mapper = new PriceResponseMapper();

    @Test
    void mapsAllDomainFieldsToResponse() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        Price price = new Price(1L, start, end, 1, 35455L, new BigDecimal("35.50"), "EUR");

        PriceResponse response = mapper.toResponse(price);

        assertThat(response.getBrandId()).isEqualTo(1L);
        assertThat(response.getProductId()).isEqualTo(35455L);
        assertThat(response.getPriceList()).isEqualTo(1);
        assertThat(response.getStartDate()).isEqualTo(start);
        assertThat(response.getEndDate()).isEqualTo(end);
        assertThat(response.getPrice()).isEqualByComparingTo("35.50");
        assertThat(response.getCurrency()).isEqualTo("EUR");
    }
}
