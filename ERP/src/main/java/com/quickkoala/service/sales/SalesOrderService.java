package com.quickkoala.service.sales;

import java.io.File;
import java.util.List;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;

public interface SalesOrderService {
    List<String> saveOrder(List<ClientsOrdersDTO> orders, String token);
    List<ClientsOrdersDTO> parseExcelFile(File file);
    List<ClientsOrderProductsDTO> getOrderProductsByOrderId(String orderId);
    //List<ClientsOrdersDTO> findAll();
}
