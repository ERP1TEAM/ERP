package com.quickkoala.service.release;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.release.ViewReleaseCompleteEntity;

public interface ViewReleaseCompleteService{
	Page<ViewReleaseCompleteEntity> getAll(int pg, int size,String select, String param);
	
	
}
