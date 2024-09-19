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
	private String product_code;
	
	@Column(name="total_qty", nullable=false)
	private int total_qty;
	
	@Column(name="available_qty", nullable=false)
	private int available_qty;

	@Column(name="unavailable_qty", nullable=false)
	private int unavailable_qty;
	
	@Column(name="safety_qty", nullable=false)
	private int safety_qty;
	
    @Column(name = "manager", length = 100, nullable = false)
    private String manager;
}
	
