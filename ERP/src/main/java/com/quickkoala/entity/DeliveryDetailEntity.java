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
@Table(name = "delivery_detail")
@Data
public class DeliveryDetailEntity {

    @Id
    @Column(name = "code", length = 14, nullable = false)
    private String code;

    @Column(name = "order_number", length = 14, nullable = false)
    private String orderNumber;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "date", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime date;
}