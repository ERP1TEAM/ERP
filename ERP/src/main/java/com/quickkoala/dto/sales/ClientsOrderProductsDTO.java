package com.quickkoala.dto.sales;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsOrderProductsDTO {
    private String productCode;
    private String productName;
    private int qty;
    private String status;  // 처리현황을 저장할 필드 추가
    private String managerMemo;
    private String clientMemo;
    
}
