package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseCompleteProductsEntity;



@Repository
public interface ViewReleaseCompleteProductsRepository extends JpaRepository<ViewReleaseCompleteProductsEntity,Integer> {
	
	List<ViewReleaseCompleteProductsEntity> findByRelNumber(String rnum);
}
