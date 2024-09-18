package com.quickkoala.entity.supplier;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "purchase")
public class PurchaseEntity {

	@Id
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;

	@Column(name = "supplier_code", length = 10, nullable = false)
	private String supplierCode;

	@Column(name = "product_code", length = 10, nullable = false)
	private String productCode;

	@Column(name = "manager", length = 30, nullable = false)
	private String manager;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "total_price", nullable = false)
	private int totalPrice;

	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;

	@Column(name = "expected_date", nullable = false)
	private String expectedDate;

	@Column(name = "status", length = 10, nullable = false)
	private String status;

	@Column(name = "memo")
	private String memo;
}
