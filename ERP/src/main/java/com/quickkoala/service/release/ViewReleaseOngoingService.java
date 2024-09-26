package com.quickkoala.service.release;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.release.ViewReleaseOngoingEntity;

public interface ViewReleaseOngoingService{
	
	public Page<ViewReleaseOngoingEntity> getAll(int pg, int size,String select, String param);
	
	
	
}
