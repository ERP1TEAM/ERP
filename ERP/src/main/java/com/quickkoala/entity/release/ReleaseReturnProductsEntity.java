package com.quickkoala.entity.release;

import java.time.LocalDateTime;

import com.quickkoala.entity.order.OrderEntity.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "release_return_products")
public class ReleaseReturnProductsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "idx", length = 8, nullable = false)
	private int number;
	
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "lot_number", length = 20, nullable = false)
	private String lotNumber;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name="product_code", nullable=false)
	private String productCode;
	
	@Column(name="supplier_code", nullable=false)
	private String supplierCode;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "qty", nullable = false)
	private int qty;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "reason", nullable = false)
	private ReleaseReturnReason reason;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "status", nullable = false)
	private ReleaseReturnStatus status;
	
	public enum ReleaseReturnReason{
		상품불량,
		단순변심,
		기타
	}
	
	public enum ReleaseReturnStatus{
		대기,
		폐기,
		입고
	}
}
