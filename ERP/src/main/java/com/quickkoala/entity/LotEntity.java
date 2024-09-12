package com.quickkoala.entity;

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
@Table(name = "lot")
public class LotEntity {
	@Id
	@Column(name = "lot_number", nullable = false, length = 18)
	private String lotNumber;

	@Column(name = "product_code", nullable = false, length = 8)
	private String productCode;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "receive_date", nullable = false)
	private LocalDateTime receiveDate;
}
