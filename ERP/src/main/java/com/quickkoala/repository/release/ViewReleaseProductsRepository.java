package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;



@Repository
public interface ViewReleaseProductsRepository extends JpaRepository<ViewReleaseProductsEntity,Integer> {
	
	List<ViewReleaseProductsEntity> findByRelNumberOrderByLotNumberDesc(String rnum);

	
}
