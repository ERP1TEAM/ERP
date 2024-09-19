package com.quickkoala.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.sales.SalesEntity;
import com.quickkoala.repository.sales.SalesRepository;

@Service
public class SalesServiceImpl implements SalesService {
	
    @Autowired
    private SalesRepository salesRepository;

    public Optional<SalesEntity> findByCode(String code) {
        return salesRepository.findByCode(code);
    }
    
}
