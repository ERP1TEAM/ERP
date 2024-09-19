package com.quickkoala.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.member.MemberDTO;
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
    
    // 사용자 역할, 이름, 회사 정보 가져오기
    @Override
    public MemberDTO getMemberInfo(String userId) {
        // 사용자 정보를 Optional로 가져오기
        Optional<MemberEntity> memberOpt = memberRepository.findByUserId(userId);

        // Optional에서 필요한 데이터가 있을 경우 DTO로 변환하여 반환
        return memberOpt.map(member -> 
            new MemberDTO(member.getRole(), member.getCode(), member.getName())
        ).orElse(null);  // 데이터가 없으면 null 반환
    }
    
}
