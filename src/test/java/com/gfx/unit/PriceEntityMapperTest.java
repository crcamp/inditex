package com.gfx.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.gfx.domain.model.Price;
import com.gfx.infrastructure.persistence.PriceEntityMapper;
import com.gfx.infrastructure.persistence.entity.PriceEntity;

class PriceEntityMapperTest {

    private final PriceEntityMapper mapper = new PriceEntityMapper();

    @Test
    void mapsAllEntityFieldsToDomain() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        PriceEntity entity = new PriceEntity();
        ReflectionTestUtils.setField(entity, "brandId", 1L);
        ReflectionTestUtils.setField(entity, "startDate", start);
        ReflectionTestUtils.setField(entity, "endDate", end);
        ReflectionTestUtils.setField(entity, "priceList", 2);
        ReflectionTestUtils.setField(entity, "productId", 35455L);
        ReflectionTestUtils.setField(entity, "price", new BigDecimal("25.45"));
        ReflectionTestUtils.setField(entity, "currency", "EUR");

        Price result = mapper.toDomain(entity);

        assertThat(result).isEqualTo(new Price(1L, start, end, 2, 35455L, new BigDecimal("25.45"), "EUR"));
    }
}
