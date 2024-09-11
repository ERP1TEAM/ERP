package com.quickkoala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveReturnEntity;

@Repository
public interface ReceiveReturnRepository extends JpaRepository<ReceiveReturnEntity, String>{
	
}
