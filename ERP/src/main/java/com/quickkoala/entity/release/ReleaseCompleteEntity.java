package com.quickkoala.entity.release;

import java.time.LocalDateTime;

import com.quickkoala.entity.order.OrderEntity.OrderStatus;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "release_complete")
public class ReleaseCompleteEntity {
	
	@Id
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "memo", nullable = true)
	private String memo;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;

}
