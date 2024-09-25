package com.quickkoala.entity.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="view_lot_view_product_stock_supplier")
public class ViewLotViewProductStockSupplierEntity {
	
	@Id
	@Column(name = "lot_number",length = 20, nullable=false)
	private String lotNumber;
 	
	@Column(name = "product_code",length = 8, nullable=false)
    private String productCode;

 	@Column(name = "product_name",length = 200, nullable=false)
    private String productName;

 	@Column(name = "supplier_code",length = 6, nullable=false)
    private String supplierCode;

 	@Column(name = "supplier_name",length = 255, nullable=false)
 	private String supplierName;
 	
 	@Column(name = "location_code",length = 8)
    private String locationCode;

 	@Column(name = "lot_quantity ", nullable=false)
 	private int lotQuantity;
 	
 	@Column(name = "safety_qty ", nullable=false)
    private int safetyQty;
}