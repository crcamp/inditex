package com.gfx.infrastructure.controller;

import org.springframework.stereotype.Component;

import com.gfx.domain.model.Price;
import com.gfx.infrastructure.dto.PriceResponse;

@Component
public class PriceResponseMapper {

    public PriceResponse toResponse(Price price) {
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
