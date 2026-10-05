package com.gfx.common.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Long productId, Long brandId, String applicationDate) {
        super("No price found for productId=%d, brandId=%d, date=%s"
                .formatted(productId, brandId, applicationDate));
    }
}
