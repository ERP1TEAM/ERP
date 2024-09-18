package com.quickkoala.repository.member;

import com.quickkoala.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	boolean existsByUserId(String userId);
}
