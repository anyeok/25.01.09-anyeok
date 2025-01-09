package com.example.anyeok.domain.member.controller;

import com.example.anyeok.domain.member.dto.MemberDTO;
import com.example.anyeok.domain.member.request.MemberCreate;
import com.example.anyeok.domain.member.service.MemberService;
import com.example.anyeok.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public RsData join(@Valid @RequestBody MemberCreate memberCreate) {
        // 회원가입에 필요한 필드 나열
        String username = memberCreate.getUsername();
        String password = memberCreate.getPassword();

        // 회원가입
        MemberDTO memberDTO = this.memberService.join(username, password);

        if (memberDTO == null) {
            return RsData.of("400", "이미 존재하는 사용자입니다.");
        }
        return RsData.of("200", "회원가입 성공", memberDTO);
    }

}
