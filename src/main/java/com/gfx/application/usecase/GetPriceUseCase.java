package com.gfx.application.usecase;

import java.time.LocalDateTime;

import com.gfx.domain.model.Price;

public interface GetPriceUseCase {
    Price getPrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
