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
@Table(name="stock")
public class StockEntity {
	
	@Id
	@Column(name="product_code", length=8, nullable=false)
	private String productCode;
	
	@Column(name = "total_qty", nullable=false)
	private int totalQty;
	
	@Column(name="available_qty", nullable=false)
	private int availableQty;

	@Column(name="unavailable_qty", nullable=false)
	private int unavailableQty;
	
	@Column(name="safety_qty", nullable=false)
	private int safetyQty;
	
    @Column(name = "manager", length = 255, nullable = false)
    private String manager;
}
	
