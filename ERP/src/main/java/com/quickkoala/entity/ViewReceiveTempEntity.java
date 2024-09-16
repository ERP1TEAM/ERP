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
@Table(name = "view_receive_temp")
@Data
public class ViewReceiveTempEntity {

	@Id
    @Column(name = "code")
    private String code;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "supplier_name")
    private String supplierName;
    
    @Column(name = "delivery_code")
    private String deliveryCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "wt_quantity")
    private Integer wtQuantity;

    @Column(name = "date")
    private java.util.Date date;

    @Column(name = "manager")
    private String manager;
}