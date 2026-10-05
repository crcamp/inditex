package com.gfx.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gfx.common.mapper.PriceMapper;
import com.gfx.domain.model.Price;
import com.gfx.domain.repository.PriceRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class PricePersistenceAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceJpaRepository
                .findTopApplicablePrice(applicationDate, productId, brandId)
                .map(priceMapper::toDomain);

    }
    
}
