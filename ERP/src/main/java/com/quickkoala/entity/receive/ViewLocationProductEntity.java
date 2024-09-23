package com.quickkoala.entity.receive;

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
@Table(name = "view_location_product")
public class ViewLocationProductEntity {
	@Id
	@Column(name = "location_code")
	private String locationCode;

	@Column(name = "product_code")
	private String productCode;
}
