package com.quickkoala.repository.release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ReleaseCompleteEntity;

@Repository
public interface ReleaseCompleteRepository extends JpaRepository<ReleaseCompleteEntity,String> {

}
