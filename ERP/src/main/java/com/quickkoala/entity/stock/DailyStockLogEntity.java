package com.quickkoala.entity.stock;

import java.time.LocalDate;

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
@Table(name="daily_stock_log")
public class DailyStockLogEntity {
	
	@Id
	@Column(name="product_code", length=8, nullable=false)
	private String productCode;
	
	@Id
	@Column(name = "log_date", nullable = false)
	private LocalDate logDate;
	
    @Column(name = "available_qty", nullable = false)
    private Integer availableQty;

    @Column(name = "unavailable_qty", nullable = false)
    private Integer unavailableQty;

    @Column(name = "safety_qty", nullable = false)
    private Integer safetyQty;

    @Column(name = "manager", length = 255, nullable = false)
    private String manager;

}
