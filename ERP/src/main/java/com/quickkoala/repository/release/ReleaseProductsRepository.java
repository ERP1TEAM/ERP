package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseProductsRepository extends JpaRepository<ReleaseProductsEntity,Integer> {
	
	List<ReleaseProductsEntity>findByRelNumberAndLotNumber(String rCode,String lCode);
	
	
}
