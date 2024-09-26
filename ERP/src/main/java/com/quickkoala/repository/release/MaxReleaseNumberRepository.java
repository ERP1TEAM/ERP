package com.quickkoala.repository.release;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.MaxOrderNumberEntity;
import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.release.MaxReleaseNumberEntity;

@Repository
public interface MaxReleaseNumberRepository extends JpaRepository<MaxReleaseNumberEntity,LocalDate> {
	
	MaxReleaseNumberEntity findByDt(LocalDate date);

}
