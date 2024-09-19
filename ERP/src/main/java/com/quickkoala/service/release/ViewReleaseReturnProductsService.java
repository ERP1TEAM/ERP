package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.dto.release.ReleaseReturnDto;

public interface ViewReleaseReturnProductsService {
	
	List<ReleaseReturnDto> getAll(int pg,int size);
	
}
