package com.quickkoala.entity.release;

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
@Table(name = "release_cancel")
public class ReleaseCancelEntity {
	
	@Id
	@Column(name = "rel_number", length = 14, nullable = false)
	private String relNumber;
	
	@Column(name = "order_number", length = 14, nullable = false)
	private String orderNumber;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "who", nullable = false)
	private ReleaseCancelWho who;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "reason", nullable = false)
	private ReleaseCancelReason reason;
	
	@Column(name = "dt", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dt;
	
	@Column(name = "sales_code", length = 20, nullable = false)
	private String salesCode;
	
	@Column(name = "manager", length = 20, nullable = false)
	private String manager;
	
	@Column(name = "memo", nullable = true)
	private String memo;
	
	public enum ReleaseCancelReason{
		고객변심,
		재고부족,
		공급지연,
		제품문제,
		잘못된상품배정,
		결제실패,
		기타
		
	}
	
	public enum ReleaseCancelWho{
		고객,
		관리자,
		공급업체,
		기타
	}

}
