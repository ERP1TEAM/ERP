package com.quickkoala.entity.order;

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
@Table(name = "order_cancel")
public class OrderCancelEntity {
	
	@Id
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Column(name = "order_id", length = 20, nullable = false)
	private String orderId;
	
	@Column(name = "manager", length = 30, nullable = false)
	private String manager;

	@Column(name = "memo", nullable = true, columnDefinition="text")
	private String memo;
	
	@Column(name = "dt", nullable = false, columnDefinition="datetime default now()")
	private LocalDateTime dt;
	
	@Column(name = "total_price", nullable = false)
	private Integer totalPrice;
	
	@Column(name = "sales_code", length = 10, nullable = false)
	private String salesCode;
	
	
	

}
