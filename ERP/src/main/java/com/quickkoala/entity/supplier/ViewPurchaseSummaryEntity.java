package com.quickkoala.entity.supplier;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "view_purchase_summary")
@Data  // Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메서드 자동 생성
public class ViewPurchaseSummaryEntity {

	@Id
    private String orderNumber;

    private String productCode;
    private String productName;
    private int quantity;
    private int price;
    private int totalPrice;
    private LocalDateTime date;
    private int totalWtQuantity;
}