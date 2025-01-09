package com.example.anyeok.global.jwt;

import com.example.anyeok.domain.member.entity.Member;
import com.example.anyeok.global.util.Util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyOrigin;

    private SecretKey cachedSecretKey;
    private static final int A_DAY = 60 * 60 * 24;

    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyOrigin.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String genRefreshToken(Member member) {
        return genToken(member, A_DAY);
    }

    public String genAccessToken(Member member) {
        return genToken(member, A_DAY);
    }


    public String genToken (Member member, int seconds) {
        Map<String, Object> claims = new HashMap<>();
        /*
            토큰에 ID, EMAIL, ROLE 등록
            토큰에 저장될 사용자 정보는 키-값 쌍을 저장하는 Map으로 저장됨

            ROLE: 스프링 시큐리티의 권한 인가(Authorization)는 "ROLE_"의 접두사를 자동으로 덧붙이므로, 추가해준다.
         */
        claims.put("id", member.getId());
        claims.put("email", member.getUsername());

        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        return Jwts.builder()
                .claim("body", Util.json.toStr(claims))
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean verify(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .setAllowedClockSkewSeconds(A_DAY)  // 하루 24h 내에 발급된 토큰 한정
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .setAllowedClockSkewSeconds(A_DAY)  //허용된 시간 차이(스큐) 설정 (24시간)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);

        return Util.toMap(body);
    }
}