package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewReceiveEntity;

@Repository
public interface ViewReceiveRepository extends JpaRepository<ViewReceiveEntity, String>{
	List<ViewReceiveEntity> findAllByOrderByReceiveCodeDesc();
	long count();
	Page<ViewReceiveEntity> findAllByOrderByReceiveCodeDesc(Pageable pageable);
}
