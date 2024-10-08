package com.quickkoala.entity.sales;

import java.time.LocalDateTime;
import java.util.List;

import com.quickkoala.entity.order.OrderEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private LocalDateTime orderDate;   // 주문 날짜 추가
    
    @Column(name="created_dt")
    private LocalDateTime createdDt;
    
    @Column(name = "client_memo")
    private String clientMemo; // 주문자 메모 필드
    
    private String manager;
    
    @Column(name = "manager_company_code") 
    private String code;
    
    @Column(name = "manager_memo")
    private String managerMemo;

    @OneToMany(mappedBy = "clientsOrders", cascade = CascadeType.ALL)
    private List<ClientsOrderProductsEntity> products;
    
    // OrderEntity와 일대일 관계 설정 (외래 키)
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id") // OrderEntity의 order_id를 외래 키로 사용
    private OrderEntity orderEntity;

}
