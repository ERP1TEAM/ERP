package com.quickkoala.service.release;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.release.ReleaseOngoingDto;

public interface ViewReleaseOngoingService{
	
	public List<ReleaseOngoingDto> getAll(int pg, int size);
	
	
}
