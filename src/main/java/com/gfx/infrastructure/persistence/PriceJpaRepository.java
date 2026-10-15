package com.gfx.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gfx.infrastructure.persistence.entity.PriceEntity;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    Optional<PriceEntity> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);
}
