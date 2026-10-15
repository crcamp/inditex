package com.gfx.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.gfx.domain.model.Price;
import com.gfx.infrastructure.persistence.entity.PriceEntity;

@Component
public class PriceEntityMapper {

    public Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPrice(),
                entity.getCurrency());
    }
}
