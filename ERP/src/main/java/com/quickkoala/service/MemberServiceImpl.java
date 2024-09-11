package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
    @Autowired
    private MemberRepository memberRepository;
    
    //관리자 등록
    @Override
    public void saveMember(MemberEntity member) {
        memberRepository.save(member);
    }
    //관리자 등록 아이디 중복체크
    @Override
    public boolean isUserIdExists(String userId) {
        return memberRepository.existsByUserId(userId);
    }
    //관리자 모든 데이터
    @Override
    public List<MemberEntity> getAllData() {
        return memberRepository.findAll();
    }
    
}
