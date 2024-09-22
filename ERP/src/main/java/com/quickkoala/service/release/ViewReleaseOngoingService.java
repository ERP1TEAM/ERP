package com.quickkoala.service.release;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;

public interface ViewReleaseOngoingService{
	
	public Page<ViewReleaseOngoingEntity> getAll(int pg, int size,String select, String param);
	public  List<ViewReleaseProductsEntity> getProducts(String relNumber);
	
	
}
