package com.quickkoala.entity.release;

import java.time.LocalDateTime;

import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseRefundReason;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseRefundStatus;

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
@Table(name = "view_release_return_products")
public class ViewReleaseReturnProductsEntity {
	@Id
	@Column(name = "idx", length = 8, nullable = false)
	private int idx;
	
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "lot_number", length = 20, nullable = false)
	private String lotNumber;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "qty", nullable = false)
	private int qty;
	
	@Column(name = "product_code", length = 8, nullable = false)
	private String productCode;
	
	@Column(name = "product_name", length = 200, nullable = false)
	private String productName;
	
	@Column(name = "supplier_code", length = 6, nullable = false)
	private String supplierCode;
	
	@Column(name = "supplier_name", length = 255, nullable = false)
	private String supplierName;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "reason", nullable = false)
	private ReleaseRefundReason reason;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "status", nullable = false)
	private ReleaseRefundStatus status;

}
