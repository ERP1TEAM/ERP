package com.quickkoala.entity.stock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="view_daily_stock_summary")
public class ViewDailyStockSummaryEntity {
	
	@Id
    @Column(name="stock_date", nullable=false)
    private LocalDate stockDate;

    @Column(name="product_code", length=8, nullable=false)
    private String productCode;

    @Column(name="total_qty", nullable=true)
    private Integer totalQty;

    @Column(name="available_qty", nullable=false)
    private Integer availableQty;

    @Column(name="unavailable_qty", nullable=false)
    private Integer unavailableQty;

    @Column(name="safety_qty", nullable=false)
    private Integer safetyQty;

    @Column(name="return_qty", nullable=false)
    private Long returnQty = 0L;

    @Column(name="received_qty", nullable=false)
    private Long receivedQty = 0L;

    @Column(name="shipped_qty", nullable=false)
    private Long shippedQty = 0L;

    @Column(name="release_date", nullable=true)
    private LocalDateTime releaseDate;
}
