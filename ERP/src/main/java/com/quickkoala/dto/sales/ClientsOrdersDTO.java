package com.quickkoala.dto.sales;

import java.time.LocalDate;
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
    private LocalDate orderDate;
    private List<ClientsOrderProductsDTO> products;
}