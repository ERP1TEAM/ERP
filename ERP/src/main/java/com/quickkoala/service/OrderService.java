package com.quickkoala.service;

import java.io.File;
import java.util.List;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;

public interface OrderService {
    void saveOrder(List<ClientsOrdersDTO> orders);
    List<ClientsOrdersDTO> parseExcelFile(File file);
    List<ClientsOrderProductsDTO> getOrderProductsByOrderId(String orderId);
    //List<ClientsOrdersDTO> findAll();
}
