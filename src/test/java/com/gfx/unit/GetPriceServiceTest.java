package com.gfx.unit;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gfx.application.service.GetPriceService;
import com.gfx.common.exception.PriceNotFoundException;
import com.gfx.domain.model.Price;
import com.gfx.domain.repository.PriceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class GetPriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private GetPriceService service;

    private static final LocalDateTime DATE = LocalDateTime.of(2020, 6, 14, 10, 0);
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @Test
    void returnsPriceWhenFound() {
        Price expected = new Price(BRAND_ID, DATE, DATE.plusDays(1), 1, PRODUCT_ID, new BigDecimal("35.50"), "EUR");
        when(priceRepository.findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Optional.of(expected));

        Price result = service.getPrice(DATE, PRODUCT_ID, BRAND_ID);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void throwsWhenNoPriceExists() {
        when(priceRepository.findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getPrice(DATE, PRODUCT_ID, BRAND_ID))
                .isInstanceOf(PriceNotFoundException.class);
    }

}
