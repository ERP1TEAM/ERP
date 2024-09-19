package com.quickkoala.token.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.repository.member.MemberRepository;



@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

		public CustomMemberDetailsService(MemberRepository MemberRepository) {
			this.memberRepository = MemberRepository;
		}

		@Override
	    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
	        MemberEntity member = memberRepository.findByUserId(userId)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

	        // 사용자의 역할을 가져옵니다.
	        String role = member.getRole();
	        
	        // 권한을 설정합니다.
	        GrantedAuthority authority = new SimpleGrantedAuthority(role);

	        // UserDetails 객체를 반환합니다.
	        return User
	                .withUsername(member.getUserId())
	                .password(member.getPassword())
	                .authorities(Collections.singletonList(authority)) // 단일 권한을 리스트로 설정합니다.
	                .accountExpired(false)
	                .accountLocked(false)
	                .credentialsExpired(false)
	                .disabled(false)
	                .build();
	    }
}
