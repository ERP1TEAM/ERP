package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewReceiveReturnEntity;

@Repository
public interface ViewReceiveReturnRepository extends JpaRepository<ViewReceiveReturnEntity, String>{
	List<ViewReceiveReturnEntity> findAllByOrderByReturnDateDesc();
	Page<ViewReceiveReturnEntity> findAllByOrderByReturnDateDesc(Pageable pageable);
}
