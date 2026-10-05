package com.gfx.common.mapper;

import org.springframework.stereotype.Component;

import com.gfx.domain.model.Price;
import com.gfx.infrastructure.persistence.entity.PriceEntity;
import com.gfx.prices.infrastructure.dto.PriceResponse;

@Component 
public class PriceMapper {
    
    public Price toDomain(PriceEntity entity)
    {
        return new Price(
            entity.getBrandId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getPriceList(),
            entity.getProductId(),
            entity.getPrice(),
            entity.getCurrency()
        );
    }

    public PriceResponse toResponse(Price price)
    {
        return new PriceResponse()
            .productId(price.productId())
            .brandId(price.brandId())
            .priceList(price.priceList())
            .startDate(price.startDate())
            .endDate(price.endDate())
            .price(price.price())
            .currency(price.currency());
    }
    
}
