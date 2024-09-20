package com.quickkoala.service.client;

import java.util.Optional;

import com.quickkoala.entity.sales.SalesEntity;

public interface SalesService {
	Optional<SalesEntity> findByCode(String code);
}
