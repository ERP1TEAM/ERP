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
@Table(name = "view_delivery_detail")
@Data
public class ViewDeliveryDetailEntity {

	@Id
    @Column(name = "delivery_code", length = 14, nullable = false)
    private String deliveryCode;

    @Column(name = "order_number", length = 14, nullable = false)
    private String orderNumber;
    
    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "product_code", length = 8, nullable = false)
    private String productCode;

    @Column(name = "product_name", length = 200, nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
