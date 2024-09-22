package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;



@Repository
public interface ViewReleaseProductsRepository extends JpaRepository<ViewReleaseProductsEntity,Integer> {
	
	List<ViewReleaseProductsEntity> findByRelNumberOrderByLotNumberDesc(String rnum);
	Page<ViewReleaseProductsEntity> findAll(Pageable pageable);
	Page<ViewReleaseProductsEntity> findByRelNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseProductsEntity> findByOrderNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseProductsEntity> findByLotNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseProductsEntity> findByProductNameContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseProductsEntity> findBySupplierNameContainingOrderByRelNumberDesc(String param, Pageable pageable);
}
