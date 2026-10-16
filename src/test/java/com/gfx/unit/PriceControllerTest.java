package com.gfx.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.gfx.domain.exception.PriceNotFoundException;
import com.gfx.domain.model.Price;
import com.gfx.domain.usecase.GetPriceUseCase;
import com.gfx.infrastructure.controller.GlobalExceptionHandler;
import com.gfx.infrastructure.controller.PriceController;
import com.gfx.infrastructure.controller.PriceResponseMapper;

@WebMvcTest(PriceController.class)
@Import({PriceResponseMapper.class, GlobalExceptionHandler.class})
class PriceControllerTest {

    private static final String DATE = "2020-06-14T10:00:00";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPriceUseCase getPriceUseCase;

    @Test
    void returnsPriceWhenFound() throws Exception {
        LocalDateTime date = LocalDateTime.parse(DATE);
        when(getPriceUseCase.getPrice(any(), any(), any()))
                .thenReturn(new Price(1L, date, date.plusDays(1), 1, 35455L, new BigDecimal("35.50"), "EUR"));

        mockMvc.perform(get("/api/prices")
                .param("applicationDate", DATE).param("productId", "35455").param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void returnsNotFoundWhenPriceDoesNotExist() throws Exception {
        when(getPriceUseCase.getPrice(any(), any(), any()))
                .thenThrow(new PriceNotFoundException(35455L, 1L, LocalDateTime.parse(DATE)));

        mockMvc.perform(get("/api/prices")
                .param("applicationDate", DATE).param("productId", "35455").param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void returnsBadRequestWhenParameterMissing() throws Exception {
        mockMvc.perform(get("/api/prices").param("productId", "35455"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void returnsBadRequestWhenParameterHasWrongType() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("applicationDate", DATE).param("productId", "abc").param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
