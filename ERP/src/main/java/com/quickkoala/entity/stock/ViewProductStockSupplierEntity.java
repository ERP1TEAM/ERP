package com.quickkoala.entity.stock;

import com.quickkoala.entity.stock.ProductEntity.UseFlag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="view_product_stock_supplier")
public class ViewProductStockSupplierEntity {

	 	@Id
	 	@Column(name = "product_code",length = 8, nullable=false)
	    private String productCode;

	 	@Column(name = "product_name",length = 200, nullable=false)
	    private String productName;

	 	@Column(name = "supplier_code",length = 6, nullable=false)
	    private String supplierCode;

	 	@Column(name = "supplier_name",length = 255, nullable=false)
	 	private String supplierName;
	 	
	 	@Column(name = "location_code",length = 8, nullable=false)
	    private String locationCode;

	 	@Column(name = "category_code",length = 6, nullable=false)
	    private String classificationCode;

	 	@Column(name = "price", nullable=false)
	    private int price;

	 	@Column(name = "total_qty", nullable=false)
	    private int totalQty;

	 	@Column(name = "available_qty", nullable=false)
	    private int availableQty;

	 	@Column(name = "unavailable_qty", nullable=false)
	    private int unavailableQty;

	 	@Column(name = "safety_qty", nullable=false)
	    private int safetyQty;

	 	@Column(name = "use_flag", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private UseFlag useFlag;
}
