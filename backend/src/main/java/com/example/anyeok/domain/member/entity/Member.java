package com.example.anyeok.domain.member.entity;

import com.example.anyeok.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true) // BaseEntity에 있는 ToString 가져옴
@Component
public class Member extends BaseEntity {
    private String username;
    private String password;

    private String refreshToken;
}