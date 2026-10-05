package com.gfx.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gfx.infrastructure.persistence.entity.PriceEntity;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT p FROM PriceEntity p
            WHERE p.brandId = :brandId
              AND p.productId = :productId
              AND :applicationDate BETWEEN p.startDate AND p.endDate
            ORDER BY p.priority DESC
            LIMIT 1
            """)
    Optional<PriceEntity> findTopApplicablePrice(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );
    
}
