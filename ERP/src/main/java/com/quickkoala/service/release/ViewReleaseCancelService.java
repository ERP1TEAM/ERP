package com.quickkoala.service.release;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.release.ViewReleaseCancelEntity;

public interface ViewReleaseCancelService{
	
	public Page<ViewReleaseCancelEntity> getAll(int pg, int size,String select, String param);
}
