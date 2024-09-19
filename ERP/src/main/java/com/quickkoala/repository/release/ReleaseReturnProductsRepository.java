package com.quickkoala.repository.release;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseReturnProductsRepository extends JpaRepository<ReleaseReturnProductsEntity,Integer> {

}
