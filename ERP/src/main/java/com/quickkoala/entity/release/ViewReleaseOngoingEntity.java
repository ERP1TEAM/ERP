package com.quickkoala.entity.release;

import java.time.LocalDateTime;

import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;

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
@Table(name = "view_release_ongoing")
public class ViewReleaseOngoingEntity {
	//private String number,orderNumber,orderId,salesCode, salesName,dt,status,manager,memo;
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
	
	@Column(name="sales_name", length=255,nullable=false)
	private String salesName;


}
