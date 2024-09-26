package com.quickkoala.service.client;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.client.SalesEntity;

public interface SalesService {
	Page<SalesEntity> getPaginatedData(int pno, int size);

	Page<SalesEntity> getPaginatedData(int pno, int size, String code, String word);
	
	Optional<SalesEntity> findByCode(String code);
	
	SalesEntity addSales(SalesEntity salesEntity);
	
	SalesEntity getOne(String code);
	
	SalesEntity modifySales(SalesEntity salesEntity, String code);
}
