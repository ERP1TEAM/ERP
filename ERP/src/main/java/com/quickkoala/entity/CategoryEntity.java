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
@Table(name="category")
public class CategoryEntity {
	
	@Id
	@Column(name="code", length=5, nullable=false)
	private String code;
	
	@Column(name="main_code", length=3, nullable=false)
	private String mainCode;
	
	@Column(name="main_name", length=50, nullable=false)
	private String mainName;
	
	@Column(name="sub_code", length=2, nullable=false)
	private String subCode;
	
	@Column(name="sub_name", length=50, nullable=false)
	private String subName;
	
	@Column(name = "use_flag", nullable = false)
    @Enumerated(EnumType.STRING)
    private UseFlag useFlag;
}
