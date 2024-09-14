package com.quickkoala.entity;

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
@Table(name = "view_receive_summary")
public class ViewReceiveSummaryEntity {
	@Id
	@Column(name = "order_number")
	private String orderNumber;
	
	@Column(name = "product_name")
	private String productName;

	@Column(name = "purchase_quantity")
	private Integer purchaseQuantity;

	@Column(name = "total_wt_quantity")
	private Integer totalWtQuantity;

	@Column(name = "total_receive_quantity")
	private Integer totalReceiveQuantity;

	@Column(name = "total_return_quantity")
	private Integer totalReturnQuantity;
}
