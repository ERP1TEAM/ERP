package com.quickkoala.entity.order;

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
@Table(name = "view_order_products")
public class ViewOrderProductsEntity {
	
	@Id
	@Column(name="item_id", nullable=false)
	private Integer itemId;
	
	@Column(name = "product_code", length = 8, nullable = false)
	private String productCode;
	
	@Column(name = "product_name", length = 200, nullable = false)
	private String productName;
	
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Column(name = "order_id", length = 20, nullable = false)
	private String orderId;
	
	@Column(name = "supplier_code", length = 6, nullable = false)
	private String supplierCode;
	
	@Column(name = "supplier_name", length = 255, nullable = false)
	private String supplierName;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "qty", nullable = false)
	private int qty;
	
	@Column(name = "available_qty", nullable = false)
	private int availableQty;
	
	

}
