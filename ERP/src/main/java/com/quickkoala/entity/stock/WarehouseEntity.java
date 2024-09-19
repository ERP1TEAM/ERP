package com.quickkoala.entity.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="warehouse")
public class WarehouseEntity {

	@Id
	@Column(name="code", length=2, nullable=false)
	private String code;
	
	@Column(name="name", length=200, nullable=false)
	private String name;
	
	@Column(name="memo", nullable=true, columnDefinition = "text")
	private String memo;
}
