package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseCompleteEntity;



@Repository
public interface ViewReleaseCompleteRepository extends JpaRepository<ViewReleaseCompleteEntity,String> {
	
List<ViewReleaseCompleteEntity> findAllByOrderByRelNumberDesc();

	
}
