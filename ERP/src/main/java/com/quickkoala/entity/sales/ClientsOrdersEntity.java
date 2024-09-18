package com.quickkoala.entity.sales;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clients_orders")
@Data
public class ClientsOrdersEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;  // order_id 필드를 String으로 변경

    private String name;
    private String tel;
    private String email;
    private String post;
    private String address;
    private String addressDetail;
    
    @Column(name = "order_date")
    private LocalDate orderDate;   // 주문 날짜 추가

    private String clientMemo;
    private String manager;
    private String managerCompanyCode;
    private String managerMemo;

    @OneToMany(mappedBy = "clientsOrders", cascade = CascadeType.ALL)
    private List<ClientsOrderProductsEntity> products;

}
