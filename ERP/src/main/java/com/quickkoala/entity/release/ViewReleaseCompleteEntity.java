package com.quickkoala.entity.release;

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
@Table(name = "view_release_complete")
public class ViewReleaseCompleteEntity {
	@Id
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "memo", nullable = true)
	private String memo;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "sales_code", length = 6, nullable = false)
	private String salesCode;
	
	@Column(name = "sales_name", length = 255, nullable = false)
	private String salesName;
}
