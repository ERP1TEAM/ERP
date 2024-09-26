package com.quickkoala.entity.order;

import java.time.LocalDateTime;

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
@Table(name = "order_release")
public class OrderReleaseEntity2 {
	@Id
	@Column(name = "number", length = 14, nullable = false)
	private String number;
	
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Column(name = "order_id", length = 20, nullable = false)
	private String orderId;
	
	@Column(name = "sales_code", length = 20, nullable = false)
	private String salesCode;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "status", nullable = false)
	private ReleaseStatus status;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "memo", nullable = true)
	private String memo;
	
	public enum ReleaseStatus{
		출고준비,
		출고지연,
		출고완료,
		출고취소
	}


}
