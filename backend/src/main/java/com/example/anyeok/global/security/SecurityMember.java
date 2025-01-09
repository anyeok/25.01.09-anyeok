package com.example.anyeok.global.security;

import com.example.anyeok.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityMember extends User {
    @Getter
    private long id;
    private Member member; // Member 객체 추가

    public SecurityMember(long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Member member) {
        super(username, password, authorities);
        this.id = id;
        this.member=member;
    }

    // getEmail() 메서드 추가
    public String getUsername() {
        return this.getUsername();
    }

    public Member getMember() {
        return this.member; // Member 객체 반환
    }


    public Authentication genAuthentication() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                this,
                this.getPassword(),
                this.getAuthorities()
        );

        return auth;
    }
}