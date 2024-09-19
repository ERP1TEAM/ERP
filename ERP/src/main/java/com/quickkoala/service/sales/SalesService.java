package com.quickkoala.service.sales;

import java.util.Optional;

import com.quickkoala.entity.sales.SalesEntity;

public interface SalesService {
	Optional<SalesEntity> findByCode(String code);
}
