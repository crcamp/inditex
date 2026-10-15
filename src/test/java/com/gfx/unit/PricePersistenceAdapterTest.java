package com.gfx.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.gfx.domain.model.Price;
import com.gfx.infrastructure.persistence.PriceEntityMapper;
import com.gfx.infrastructure.persistence.PricePersistenceAdapter;

@DataJpaTest
@Import({PricePersistenceAdapter.class, PriceEntityMapper.class})
class PricePersistenceAdapterTest {

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @Autowired
    private PricePersistenceAdapter adapter;

    @Test
    void picksHigherPriorityWhenRangesOverlap() {
        Optional<Price> result = adapter.findApplicablePrice(LocalDateTime.of(2020, 6, 14, 16, 0), PRODUCT_ID, BRAND_ID);

        assertThat(result).isPresent();
        assertThat(result.get().priceList()).isEqualTo(2);
        assertThat(result.get().price()).isEqualByComparingTo(new BigDecimal("25.45"));
    }

    @Test
    void picksLowerPriorityWhenOnlyOneRangeApplies() {
        Optional<Price> result = adapter.findApplicablePrice(LocalDateTime.of(2020, 6, 14, 21, 0), PRODUCT_ID, BRAND_ID);

        assertThat(result).isPresent();
        assertThat(result.get().priceList()).isEqualTo(1);
    }

    @Test
    void includesRangeBoundaries() {
        Optional<Price> result = adapter.findApplicablePrice(LocalDateTime.of(2020, 6, 14, 18, 30), PRODUCT_ID, BRAND_ID);

        assertThat(result).isPresent();
        assertThat(result.get().priceList()).isEqualTo(2);
    }

    @Test
    void returnsEmptyWhenNoRangeApplies() {
        Optional<Price> result = adapter.findApplicablePrice(LocalDateTime.of(2019, 1, 1, 10, 0), PRODUCT_ID, BRAND_ID);

        assertThat(result).isEmpty();
    }
}
