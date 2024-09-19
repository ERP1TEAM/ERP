package com.quickkoala.service.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.repository.release.ReleaseProductsRepository;

@Service
public class ReleaseProductsServiceImpl implements ReleaseProductsService{
	
	@Autowired
	private ReleaseProductsRepository releaseProductsRepository;
	
}
