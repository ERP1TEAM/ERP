package com.quickkoala.repository.release;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseReturnStatus;


@Repository
public interface ViewReleaseReturnProductsRepository extends JpaRepository<ViewReleaseReturnProductsEntity,Integer> {
	Page<ViewReleaseReturnProductsEntity> findAllByStatus(ReleaseReturnStatus status, Pageable pg);
	Page<ViewReleaseReturnProductsEntity> findByRelNumberContainingOrderByRelNumberDesc(String param, Pageable pgab);
	Page<ViewReleaseReturnProductsEntity> findByLotNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseReturnProductsEntity> findByProductNameContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseReturnProductsEntity> findBySupplierNameContainingOrderByRelNumberDesc(String param, Pageable pageable);
}
