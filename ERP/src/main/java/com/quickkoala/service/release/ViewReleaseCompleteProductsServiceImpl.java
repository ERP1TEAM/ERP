package com.quickkoala.service.release;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.release.ViewReleaseCompleteProductsEntity;
import com.quickkoala.repository.release.ViewReleaseCompleteProductsRepository;

@Service
public class ViewReleaseCompleteProductsServiceImpl implements ViewReleaseCompleteProductsService{
	
	@Autowired
	private ViewReleaseCompleteProductsRepository viewReleaseCompleteProductsRepository;
	
	public  List<ViewReleaseCompleteProductsEntity> getProducts(String relNumber){
		try {
			return viewReleaseCompleteProductsRepository.findByRelNumber(relNumber);
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
}