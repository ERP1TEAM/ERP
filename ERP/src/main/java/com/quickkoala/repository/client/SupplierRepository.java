package com.quickkoala.repository.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.client.SupplierEntity;



@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String>{
	List<SupplierEntity> findAllByOrderByCreatedDateDesc();
	
	List<SupplierEntity> findByNameContainingIgnoreCase(String term);
	
    SupplierEntity findByName(String name);
    
    Page<SupplierEntity> findAllByOrderByCodeDesc(Pageable pageable);
    
    //발주처코드로 검색
    Page<SupplierEntity> findByCodeContainingOrderByCodeDesc(String code, Pageable pageable);
    
    //발주처명으로 검색
    Page<SupplierEntity> findByNameContainingOrderByCodeDesc(String name, Pageable pageable);
}
