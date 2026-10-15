package com.gfx.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gfx.domain.exception.PriceNotFoundException;
import com.gfx.domain.model.Price;
import com.gfx.domain.repository.PriceRepository;
import com.gfx.domain.usecase.GetPriceUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPriceUseCaseImpl implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, applicationDate));
    }
}
