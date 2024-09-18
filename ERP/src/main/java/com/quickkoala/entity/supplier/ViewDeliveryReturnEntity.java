package com.quickkoala.entity.supplier;

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
@Table(name = "view_delivery_return")
public class ViewDeliveryReturnEntity {

    @Id
    @Column(name = "delivery_code", nullable = false, length = 14)
    private String deliveryCode;

    @Column(name = "order_number", nullable = false, length = 14)
    private String orderNumber;

    @Column(name = "product_code", nullable = false, length = 8)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "delivery_quantity", nullable = false)
    private int deliveryQuantity;

    @Column(name = "return_quantity", nullable = false)
    private int returnQuantity;

    @Column(name = "return_reason", nullable = false, columnDefinition = "TEXT")
    private String returnReason;
    
    @Column(name = "return_memo", nullable = true, columnDefinition = "TEXT")
    private String returnMemo;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;
}
