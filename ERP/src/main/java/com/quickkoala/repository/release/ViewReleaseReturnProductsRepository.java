package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseProductsEntity;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseRefundStatus;



@Repository
public interface ViewReleaseReturnProductsRepository extends JpaRepository<ViewReleaseReturnProductsEntity,Integer> {
	

	Page<ViewReleaseReturnProductsEntity> findAllByStatus(ReleaseRefundStatus status, Pageable pg);
	
}
