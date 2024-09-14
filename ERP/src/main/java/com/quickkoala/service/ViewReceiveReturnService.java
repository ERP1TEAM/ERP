package com.quickkoala.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.ViewReceiveReturnEntity;

public interface ViewReceiveReturnService {
	List<ViewReceiveReturnEntity> getAllData();
	Page<ViewReceiveReturnEntity> getPaginatedData(int pno, int size);
}
