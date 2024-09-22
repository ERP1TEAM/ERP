package com.quickkoala.repository.release;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseReturnProductsRepository extends JpaRepository<ReleaseReturnProductsEntity,Integer> {
	
	Optional<ReleaseReturnProductsEntity> findByRelNumberAndLotNumber(String rel, String lot);

}
