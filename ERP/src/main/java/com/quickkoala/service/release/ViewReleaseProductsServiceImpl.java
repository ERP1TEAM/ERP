package com.quickkoala.service.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.repository.release.ViewReleaseProductsRepository;

@Service
public class ViewReleaseProductsServiceImpl implements ViewReleaseProductsService{
	
	@Autowired
	private ViewReleaseProductsRepository viewReleaseProductsRepository;
	
}