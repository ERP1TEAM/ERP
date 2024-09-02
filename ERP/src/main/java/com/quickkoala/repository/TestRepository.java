package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Integer>{
	List<TestEntity> findAll();
	
}
