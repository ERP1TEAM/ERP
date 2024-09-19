package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseCancelEntity;



@Repository
public interface ViewReleaseCancelRepository extends JpaRepository<ViewReleaseCancelEntity,String> {
	
	List<ViewReleaseCancelEntity> findAllByOrderByRelNumberDesc();

	
}
