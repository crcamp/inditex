package com.gfx.domain.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.gfx.domain.model.Price;

public interface PriceRepository {

    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
    
}
