package com.quickkoala.entity;

import com.quickkoala.entity.ProductEntity.UseFlag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="location")
public class LocationEntity {
	
	@Id
	@Column(name="code", length=5, nullable=false)
	private String code;
	
	@Column(name="warehouse_code", length=3, nullable=false)
	private String warehousecode;
	
	@Column(name="rack_code", length=2, nullable=false)
	private String rackcode;
	
	@Column(name="row_code", length=2, nullable=false)
	private String rowcode;
	
	@Column(name="level_code", length=1, nullable=false)
	private String levelcode;
	
	@Column(name = "use_flag", nullable = false)
    @Enumerated(EnumType.STRING)
    private UseFlag useflag;
}
