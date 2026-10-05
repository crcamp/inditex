package com.gfx.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
    Long brandId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Integer priceList,
    Long productId,
    BigDecimal price,
    String currency
) {}
