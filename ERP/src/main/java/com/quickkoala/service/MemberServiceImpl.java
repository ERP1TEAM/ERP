package com.quickkoala.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.repository.member.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //관리자 등록
    @Override
    public void saveMember(MemberEntity member) {
    	member.setPassword(passwordEncoder.encode(member.getPassword()));
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
    
    
    //사용자인증
    public boolean authenticateUser(String userId, String password) {
        // 사용자 정보를 Optional로 가져옵니다
    	MemberEntity member = memberRepository.findByUserId(userId).orElse(null);

        // 사용자 정보가 존재하고 비밀번호가 일치하는지 확인합니다
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return true;
        }

        return false;
    }
    
    //사용자 역할 가져오기
    public String getRole(String userId) {
        // User 객체를 Optional로 조회	
        Optional<MemberEntity> memberOpt = memberRepository.findByUserId(userId);      
        // User 객체가 존재하면 role 필드를 반환
        return memberOpt.map(MemberEntity::getRole).orElse(null); // 사용자 없음에는 null 반환
    }
    
}
