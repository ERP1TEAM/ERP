package com.quickkoala.repository.release;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseCompleteEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseCompleteRepository extends JpaRepository<ReleaseCompleteEntity,String> {

}
