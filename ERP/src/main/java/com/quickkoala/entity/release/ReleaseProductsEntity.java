package com.quickkoala.entity.release;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "release_products")
public class ReleaseProductsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "idx", length = 8, nullable = false)
	private int idx;
	
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "lot_number", length = 20, nullable = false)
	private String lotNumber;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "memo", nullable = true)
	private String memo;
	
	@Column(name = "qty", nullable = false)
	private int qty;
	
	@Column(name="product_code", nullable=false)
	private String productCode;
	
	@Column(name="supplier_code", nullable=false)
	private String supplierCode;
	
	@Column(name="return_flag", nullable=false)
	private String returnFlag;
	
}
