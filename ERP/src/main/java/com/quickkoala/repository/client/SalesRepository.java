package com.quickkoala.repository.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.client.SalesEntity;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, String>{
	List<SalesEntity> findAllByOrderByCreateDateDesc();
	
	List<SalesEntity> findByNameContainingIgnoreCase(String term);
	
	SalesEntity findByName(String name);
    
    Page<SalesEntity> findAllByOrderByCodeDesc(Pageable pageable);
    
    //거래처코드로 검색
    Page<SalesEntity> findByCodeContainingOrderByCodeDesc(String code, Pageable pageable);
    
    //거래처명으로 검색
    Page<SalesEntity> findByNameContainingOrderByCodeDesc(String name, Pageable pageable);
}
