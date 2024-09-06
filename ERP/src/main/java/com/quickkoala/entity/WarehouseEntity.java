package com.quickkoala.entity;

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
@Table(name="warehouse")
public class WarehouseEntity {

	@Id
	@Column(name="code", length=3, nullable=false)
	private String code;
	
	@Column(name="name", length=5, nullable=false)
	private String name;
}
