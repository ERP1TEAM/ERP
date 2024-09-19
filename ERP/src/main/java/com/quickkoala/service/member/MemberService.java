package com.quickkoala.service.member;

import java.util.List;

import com.quickkoala.dto.member.MemberDTO;
import com.quickkoala.entity.member.MemberEntity;

public interface MemberService {
    // 모든 회원 데이터 조회
    List<MemberEntity> getAllData();
    //아이디 중복체크
    boolean isUserIdExists(String userId);
    // 회원 등록
    void saveMember(MemberEntity member);
    
    MemberDTO getMemberInfo(String userId);
}
