package com.quickkoala.service.release;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.release.ViewReleaseProductsEntity;
import com.quickkoala.repository.release.ViewReleaseProductsRepository;

@Service
public class ViewReleaseProductsServiceImpl implements ViewReleaseProductsService{
	
	@Autowired
	private ViewReleaseProductsRepository viewReleaseProductsRepository;
	
	public  List<ViewReleaseProductsEntity> getProducts(String relNumber){
		try {
			return viewReleaseProductsRepository.findByRelNumber(relNumber);
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
}