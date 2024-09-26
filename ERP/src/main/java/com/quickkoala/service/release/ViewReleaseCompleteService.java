package com.quickkoala.service.release;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.release.ReleaseCompleteDto;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;

public interface ViewReleaseCompleteService{
	Page<ViewReleaseCompleteEntity> getAll(int pg, int size,String select, String param,String startDate,String endDate);
	
	
}
