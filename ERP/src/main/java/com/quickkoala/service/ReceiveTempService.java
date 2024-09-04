package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.PurchaseDto;
import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.dto.SupplierDeliveryListDto;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.entity.ReceiveTempEntity;

public interface ReceiveTempService {
	List<ReceiveTempEntity> addAllReceive(SupplierDeliveryListDto orders);
}
