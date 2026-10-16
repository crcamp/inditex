package com.gfx.infrastructure.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.gfx.domain.usecase.GetPriceUseCase;
import com.gfx.infrastructure.api.PricesApi;
import com.gfx.infrastructure.dto.PriceResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceResponseMapper priceResponseMapper;

    @Override
    public ResponseEntity<PriceResponse> getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return ResponseEntity.ok(
                priceResponseMapper.toResponse(getPriceUseCase.getPrice(applicationDate, productId, brandId)));
    }
}
