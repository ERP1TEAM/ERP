package com.quickkoala.controller.member;

import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public String register(@RequestBody MemberEntity member) {
        memberService.saveMember(member);
        return "회원 등록 성공";
    }
}
