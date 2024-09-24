package com.quickkoala.dto.sales;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsOrdersDTO {
    private String name;
    private String tel;
    private String email;
    private String post;
    private String address;
    private String addressDetail;
    private String clientMemo;
    private String manager;           
    private String managerCompanyCode; 
    private String managerMemo;
    private LocalDateTime orderDate;
    private LocalDateTime createdDt;
    private List<ClientsOrderProductsDTO> products;
    
    private List<ClientsOrdersDTO> orders;
}
