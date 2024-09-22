package com.quickkoala.entity.release;

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
@Table(name = "view_release_products")
public class ViewReleaseProductsEntity {

	@Id
	@Column(name = "idx", length = 8, nullable = false)
	private int idx;
	
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Column(name = "lot_number", length = 20, nullable = false)
	private String lotNumber;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "memo", nullable = true)
	private String memo;
	
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
}
