package com.quickkoala.entity.stock;

import java.math.BigInteger;
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
@Table(name="view_daily_stock_summary")
public class ViewDailyStockSummaryEntity {
	
		@Id
		@Column(name = "stock_date", nullable = false)
		private LocalDate stockDate;
	 	
		@Id
	    @Column(name = "product_code", length = 8, nullable = false)
	    private String productCode;

	    @Column(name = "total_qty")
	    private Integer totalQty;

	    @Column(name = "available_qty", nullable = false)
	    private Integer availableQty;

	    @Column(name = "unavailable_qty", nullable = false)
	    private Integer unavailableQty;

	    @Column(name = "receive_return_qty", nullable = false)
	    private BigInteger receiveReturnQty = BigInteger.ZERO;

	    @Column(name = "received_qty", nullable = false)
	    private BigInteger receivedQty = BigInteger.ZERO;

	    @Column(name = "shipped_qty", nullable = false)
	    private BigInteger shippedQty = BigInteger.ZERO;

	    @Column(name = "return_qty", nullable = false)
	    private BigInteger returnQty = BigInteger.ZERO;
	}
