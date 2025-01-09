package com.example.anyeok.domain.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberCreate {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
