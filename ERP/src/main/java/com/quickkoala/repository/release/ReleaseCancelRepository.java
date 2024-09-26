package com.quickkoala.repository.release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ReleaseCancelEntity;

@Repository
public interface ReleaseCancelRepository extends JpaRepository<ReleaseCancelEntity,String> {

}
