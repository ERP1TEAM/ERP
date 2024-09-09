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

    @Override
    public void saveMember(MemberEntity member) {
        memberRepository.save(member);
    }

    @Override
    public List<MemberEntity> getAllData() {
        return memberRepository.findAll();
    }
    
}
