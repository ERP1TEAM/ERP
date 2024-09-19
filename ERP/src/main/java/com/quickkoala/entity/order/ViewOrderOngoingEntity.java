package com.quickkoala.entity.order;

import java.time.LocalDateTime;

import com.quickkoala.entity.order.OrderEntity.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "view_order_ongoing")
public class ViewOrderOngoingEntity {
	@Id
	@Column(name = "number", length = 14, nullable = false)
	private String number;
	
	@Column(name = "order_id", length = 20, nullable = false)
	private String orderId;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "status", nullable = false)
	private OrderStatus status;
	
	@Column(name = "sales_code", length = 10, nullable = false)
	private String salesCode;
	
	@Column(name = "sales_name", length = 255, nullable = false)
	private String salesName;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "approved_dt", nullable = true, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime approvedDt;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "order_total", nullable = true)
	private int orderTotal;
	
	@Column(name = "memo", nullable = true, columnDefinition="text")
	private String memo;
	

}
