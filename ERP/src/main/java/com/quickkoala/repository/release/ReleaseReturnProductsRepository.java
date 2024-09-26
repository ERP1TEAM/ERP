package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ReleaseReturnProductsEntity;

@Repository
public interface ReleaseReturnProductsRepository extends JpaRepository<ReleaseReturnProductsEntity,Integer> {
	
	List<ReleaseReturnProductsEntity> findByRelNumberAndLotNumber(String rel, String lot);

}
