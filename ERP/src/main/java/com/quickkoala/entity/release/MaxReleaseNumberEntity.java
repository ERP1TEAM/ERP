package com.quickkoala.entity.release;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "release_number")
public class MaxReleaseNumberEntity {
	@Id
	@Column(name = "dt", nullable = false)
	private LocalDate dt;
	
	@Column(name = "num", length = 3, nullable = false)
	private int num;
	
	

}
