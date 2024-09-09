package com.quickkoala.service;

import java.util.List;

import com.quickkoala.entity.member.MemberEntity;

public interface MemberService {
    // 모든 회원 데이터 조회
    List<MemberEntity> getAllData();

    // 회원 등록
    void saveMember(MemberEntity member);
}
