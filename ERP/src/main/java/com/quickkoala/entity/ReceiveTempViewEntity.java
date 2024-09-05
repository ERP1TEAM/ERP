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
@Table(name = "receive_with_temp")
public class ReceiveTempViewEntity {

	@Id
    @Column(name = "code")
    private String code;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "manager")
    private String manager;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "memo")
    private String memo;

    @Column(name = "wt_quantity")
    private Integer wtQuantity;
}
