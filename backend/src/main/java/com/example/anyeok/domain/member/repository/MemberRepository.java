package com.example.anyeok.domain.member.repository;

import com.example.anyeok.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByRefreshToken(String refreshToken);
}
