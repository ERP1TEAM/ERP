package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.dto.release.ReleaseCompleteDto;

public interface ViewReleaseCompleteService{
	List<ReleaseCompleteDto> getAll(int pg,int size);
	
	
}
