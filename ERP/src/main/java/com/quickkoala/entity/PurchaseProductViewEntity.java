package com.quickkoala.entity;

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
@Table(name = "view_purchase_product")
@Data  // Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메서드 자동 생성
public class PurchaseProductViewEntity {

	@Id
    @Column(name = "order_number", length = 14, nullable = false)
    private String orderNumber;

    @Column(name = "product_code", length = 10, nullable = false)
    private String productCode;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
}