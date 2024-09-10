package com.quickkoala.entity;

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
@Table(name = "view_purchase")
@Data
public class PurchaseViewEntity {

	 @Id
    @Column(name = "product_code")  // 상품 코드
    private String productCode;

    @Column(name = "manufacturer")  // 제조사 이름
    private String manufacturer;

    @Column(name = "product_name")  // 상품명
    private String productName;

    @Column(name = "unit_price")  // 단가
    private Integer unitPrice;
}