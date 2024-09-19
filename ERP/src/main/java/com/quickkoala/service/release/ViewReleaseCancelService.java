package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.dto.release.ReleaseCancelDto;

public interface ViewReleaseCancelService{
	
	public List<ReleaseCancelDto> getAll(int pg, int size);
}
