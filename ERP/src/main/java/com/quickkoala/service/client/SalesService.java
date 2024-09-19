package com.quickkoala.service.client;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.client.SalesEntity;

public interface SalesService {
	Page<SalesEntity> getPaginatedData(int pno, int size);

	Page<SalesEntity> getPaginatedData(int pno, int size, String code, String word);
}
