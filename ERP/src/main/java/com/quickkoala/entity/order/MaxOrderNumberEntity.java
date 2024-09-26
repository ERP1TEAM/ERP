package com.quickkoala.entity.order;

import java.time.LocalDate;
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
@Table(name = "max_order_number")
public class MaxOrderNumberEntity {
	@Id
	@Column(name = "dt", nullable = false)
	private LocalDate dt;
	
	@Column(name = "num", length = 3, nullable = false)
	private int num;
	
	

}
