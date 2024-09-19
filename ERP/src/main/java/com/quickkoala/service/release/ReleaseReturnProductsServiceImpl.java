package com.quickkoala.service.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.repository.release.ReleaseReturnProductsRepository;

@Service
public class ReleaseReturnProductsServiceImpl implements ReleaseReturnProductsService{
	
	@Autowired
	private ReleaseReturnProductsRepository releaseReturnProductsRepository;
	
}
