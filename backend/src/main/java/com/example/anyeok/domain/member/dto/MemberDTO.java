package com.example.anyeok.domain.member.dto;

import com.example.anyeok.domain.member.entity.Member;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MemberDTO {
    private final Long id;
    private final String username;
    private final String password;

    public MemberDTO (Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();

    }
}
