package com.quickkoala.service.release;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.release.ReleaseCancelDto;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;

public interface ViewReleaseCancelService{
	
	public Page<ViewReleaseCancelEntity> getAll(int pg, int size,String select, String param, String startDate, String endDate);
}
