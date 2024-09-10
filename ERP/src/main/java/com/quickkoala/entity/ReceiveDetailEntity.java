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
@Table(name = "receive_detail")
public class ReceiveDetailEntity {

	@Id
    @Column(name = "code", length = 14)
    private String code;

    @Column(name = "order_number", length = 14, nullable = false)
    private String orderNumber;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;
}