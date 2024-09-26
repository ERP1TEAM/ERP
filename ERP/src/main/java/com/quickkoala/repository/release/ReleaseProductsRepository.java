package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ReleaseProductsEntity;

@Repository
public interface ReleaseProductsRepository extends JpaRepository<ReleaseProductsEntity,Integer> {
	
	List<ReleaseProductsEntity>findByRelNumberAndLotNumber(String rCode,String lCode);
	
	
}
