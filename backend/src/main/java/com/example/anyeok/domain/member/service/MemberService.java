package com.example.anyeok.domain.member.service;

import com.example.anyeok.domain.member.dto.MemberDTO;
import com.example.anyeok.domain.member.entity.Member;
import com.example.anyeok.domain.member.repository.MemberRepository;
import com.example.anyeok.global.jwt.JwtProvider;
import com.example.anyeok.global.rsData.RsData;
import com.example.anyeok.global.security.SecurityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public MemberDTO join(String username, String password) {
        if (memberRepository.findByUsername(username).isPresent()) {
            return null;
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
        return new MemberDTO(member);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    public boolean validateToken(String accessToken) {
        return jwtProvider.verify(accessToken);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리프레시 토큰입니다."));

        String accessToken = jwtProvider.genAccessToken(member);
        return RsData.of("200", "토큰 갱신 성공", accessToken);
    }

    public SecurityMember getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);

        long id = ((Number) payloadBody.get("id")).longValue();
        String email = (String) payloadBody.get("email");
        String roles = (String) payloadBody.get("roles");

        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new SecurityMember(id, email, "", authorities, null);
    }
}
