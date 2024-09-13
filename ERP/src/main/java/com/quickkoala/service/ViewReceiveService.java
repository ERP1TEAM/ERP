package com.quickkoala.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.ViewReceiveEntity;

public interface ViewReceiveService {
	List<ViewReceiveEntity> getData();
	long getCount();
	Page<ViewReceiveEntity> getPaginatedData(int pno, int size);
}
