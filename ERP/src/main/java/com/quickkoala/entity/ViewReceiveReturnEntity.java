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
@Data
@Table(name = "view_receive_return")
public class ViewReceiveReturnEntity {
	
	@Id
    @Column(name = "return_number", nullable = false, length = 14)
    private String returnNumber;

    @Column(name = "order_number", nullable = false, length = 14)
    private String orderNumber;

    @Column(name = "supplier_name", nullable = false, length = 255)
    private String supplierName;

    @Column(name = "product_code", nullable = false, length = 8)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "return_quantity", nullable = false)
    private int returnQuantity;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Column(name = "return_reason", nullable = false, columnDefinition = "TEXT")
    private String returnReason;

    @Column(name = "return_memo", columnDefinition = "TEXT")
    private String returnMemo;

    @Column(name = "return_manager", nullable = false, length = 30)
    private String returnManager;
}
