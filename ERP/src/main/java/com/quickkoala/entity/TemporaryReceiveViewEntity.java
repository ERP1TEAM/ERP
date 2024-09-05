package com.quickkoala.entity;

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
@Table(name = "view_receive_purchase_product")
@Data
public class TemporaryReceiveViewEntity {

	@Id
    private String code; // Primary key for the view

    private String orderNumber;
    private String supplierName; // Updated to reflect supplier name correctly
    private String productCode;
    private String productName;
    private int quantity;
    private int wtQuantity;
    private LocalDateTime date;
    private String manager;
}