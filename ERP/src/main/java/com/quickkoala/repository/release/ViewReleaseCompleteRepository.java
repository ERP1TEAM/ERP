package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseCompleteEntity;



@Repository
public interface ViewReleaseCompleteRepository extends JpaRepository<ViewReleaseCompleteEntity,String> {
	
List<ViewReleaseCompleteEntity> findAllByOrderByRelNumberDesc();
Page<ViewReleaseCompleteEntity> findByRelNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
Page<ViewReleaseCompleteEntity> findByOrderNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
Page<ViewReleaseCompleteEntity> findBySalesNameContainingOrderByRelNumberDesc(String param, Pageable pageable);




	
}
