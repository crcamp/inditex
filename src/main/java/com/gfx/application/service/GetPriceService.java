package com.gfx.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gfx.application.usecase.GetPriceUseCase;
import com.gfx.common.exception.PriceNotFoundException;
import com.gfx.domain.model.Price;
import com.gfx.domain.repository.PriceRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class GetPriceService implements GetPriceUseCase {
    private final PriceRepository priceRepository;

    @Override
    public Price getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
        .orElseThrow(() -> new PriceNotFoundException(productId, brandId, applicationDate.toString()));
    }
}
