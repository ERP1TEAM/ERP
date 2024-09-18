package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.receive.ViewReceiveReturnEntity;

public interface ViewReceiveReturnService {
	List<ViewReceiveReturnEntity> getAllData();
	Page<ViewReceiveReturnEntity> getPaginatedData(int pno, int size);
	Page<ViewReceiveReturnEntity> getPaginatedData(int pno, int size, String code, String word);
}
