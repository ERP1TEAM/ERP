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
@Table(name = "receive_temp")
public class ReceiveTempEntity {

	@Id
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "order_number", length = 14, nullable = false)
    private String orderNumber;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "wt_quantity", nullable = false)
    private Integer wtQuantity;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;
}
