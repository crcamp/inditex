package com.gfx.infrastructure.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gfx.application.usecase.GetPriceUseCase;
import com.gfx.common.exception.PriceNotFoundException;
import com.gfx.common.mapper.PriceMapper;
import com.gfx.prices.infrastructure.api.PricesApi;
import com.gfx.prices.infrastructure.dto.PriceResponse;

import lombok.RequiredArgsConstructor;

@Controller 
@RequiredArgsConstructor 
public class PriceController implements PricesApi {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;


    @Override
    public ResponseEntity<PriceResponse> getPrice(LocalDateTime applicationDate, Long productId,
            Long brandId) {
        return ResponseEntity.ok(
            priceMapper.toResponse(getPriceUseCase.getPrice(applicationDate, productId, brandId))
        );
    }

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }
    
}
