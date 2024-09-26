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
@Table(name = "view_order_cancel")
public class ViewOrderCancelEntity {
	
	@Id
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Column(name = "order_id", length = 20, nullable = false)
	private String orderId;
	
	@Column(name = "sales_code", length = 10, nullable = false)
	private String salesCode;
	
	@Column(name = "sales_name", length = 255, nullable = false)
	private String salesName;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "order_total", nullable = true)
	private int orderTotal;
	
	@Column(name = "memo", nullable = true, columnDefinition="text")
	private String memo;
	

}
