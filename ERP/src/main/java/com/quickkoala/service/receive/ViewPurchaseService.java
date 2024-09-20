package com.quickkoala.service.receive;

import java.util.List;

import com.quickkoala.entity.receive.ViewPurchaseEntity;

public interface ViewPurchaseService {
	List<ViewPurchaseEntity> getAllData();
	List<ViewPurchaseEntity> getSearchData(String code, String word);
}
