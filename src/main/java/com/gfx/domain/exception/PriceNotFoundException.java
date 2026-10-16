package com.gfx.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Long productId, Long brandId, LocalDateTime applicationDate) {
        super("No price found for productId=%d, brandId=%d, date=%s"
                .formatted(productId, brandId, applicationDate));
    }
}
