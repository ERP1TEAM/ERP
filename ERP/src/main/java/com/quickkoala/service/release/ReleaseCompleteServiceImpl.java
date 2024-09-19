package com.quickkoala.service.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.repository.release.ReleaseCompleteRepository;

@Service
public class ReleaseCompleteServiceImpl implements ReleaseCompleteService{
	
	@Autowired
	private ReleaseCompleteRepository releaseCompleteRepository;
	
}
