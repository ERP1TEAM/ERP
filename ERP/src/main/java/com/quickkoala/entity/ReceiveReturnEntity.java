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
@Table(name = "receive_return")
public class ReceiveReturnEntity {

	@Id
	@Column(name = "code", nullable = false, length = 14)
	private String code;

	@Column(name = "order_number", nullable = false, length = 14)
	private String orderNumber;
	
	@Column(name = "delivery_code", nullable = false, length = 20)
	private String deliveryCode;

	@Column(name = "reason", nullable = false, columnDefinition = "TEXT")
	private String reason;
	
	@Column(name = "memo", nullable = false, columnDefinition = "TEXT")
	private String memo;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "date", nullable = false)
	private LocalDateTime date;

	@Column(name = "manager", nullable = false, length = 30)
	private String manager;

}