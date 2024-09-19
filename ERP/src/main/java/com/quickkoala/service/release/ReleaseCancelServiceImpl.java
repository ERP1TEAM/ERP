package com.quickkoala.service.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.repository.release.ReleaseCancelRepository;

@Service
public class ReleaseCancelServiceImpl implements ReleaseCancelService{
	
	@Autowired
	private ReleaseCancelRepository releaseCancelRepository;
	
}
