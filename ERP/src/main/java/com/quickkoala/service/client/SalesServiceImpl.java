package com.quickkoala.service.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.client.SalesEntity;
import com.quickkoala.repository.client.SalesRepository;

@Service
public class SalesServiceImpl implements SalesService{
	
	@Autowired
	private SalesRepository salesRepository;
	
	@Override
	public Page<SalesEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return salesRepository.findAllByOrderByCodeDesc(pageable);
	}
	
	@Override
	public Page<SalesEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<SalesEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("거래처코드")) {
			result = salesRepository.findByCodeContainingOrderByCodeDesc(word, pageable);			
		}else if(code.equals("거래처명")) {
			result = salesRepository.findByNameContainingOrderByCodeDesc(word, pageable);
		}
		return result;
	}
	
	 public Optional<SalesEntity> findByCode(String code) {
	        return salesRepository.findByCode(code);
	    }
}
