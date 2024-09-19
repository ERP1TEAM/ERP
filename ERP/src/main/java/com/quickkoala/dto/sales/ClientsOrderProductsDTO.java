package com.quickkoala.dto.sales;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsOrderProductsDTO {
    private String productCode;
    private String productName;
    private int qty;
}
