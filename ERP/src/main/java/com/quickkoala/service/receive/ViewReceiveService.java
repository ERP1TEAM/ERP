package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.receive.ViewReceiveEntity;

public interface ViewReceiveService {
	List<ViewReceiveEntity> getData();
	long getCount();
	Page<ViewReceiveEntity> getPaginatedData(int pno, int size);
	Page<ViewReceiveEntity> getPaginatedData(int pno, int size, String code, String word);
}
