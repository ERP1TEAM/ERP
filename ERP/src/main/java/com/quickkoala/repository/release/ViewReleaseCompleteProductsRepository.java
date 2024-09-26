package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.entity.release.ViewReleaseCompleteProductsEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;



@Repository
public interface ViewReleaseCompleteProductsRepository extends JpaRepository<ViewReleaseCompleteProductsEntity,Integer> {
	
	List<ViewReleaseCompleteProductsEntity> findByRelNumber(String rnum);
}
