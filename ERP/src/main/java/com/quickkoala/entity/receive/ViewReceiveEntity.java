package com.quickkoala.entity.receive;

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
@Table(name = "view_receive")
public class ViewReceiveEntity {
	@Id
	@Column(name = "receive_code")
	private String receiveCode;

	@Column(name = "order_number")
	private String orderNumber;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "receive_quantity")
	private Integer receiveQuantity;

	@Column(name = "receive_date")
	private LocalDateTime receiveDate;

	@Column(name = "manager")
	private String manager;
}
