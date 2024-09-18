package com.quickkoala.entity.receive;

import java.util.Date;

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
@Table(name = "view_purchase_detail")
public class ViewPurchaseDetailEntity {
	@Id
    @Column(name = "order_number", length = 14)
    private String orderNumber;

    @Column(name = "supplier_name", length = 255)
    private String supplierName;

    @Column(name = "product_name", length = 200)
    private String productName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "expected_date", length = 10)
    private String expectedDate;

    @Column(name = "manager", length = 30)
    private String manager;

    @Column(name = "status", length = 10)
    private String status;
}
