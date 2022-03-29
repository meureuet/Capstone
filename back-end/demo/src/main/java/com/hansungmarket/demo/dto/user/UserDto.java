package com.hansungmarket.demo.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private String nickname;
    private String username;
    private String email;

    @Builder
    private UserDto(String nickname, String username, String email) {
        this.nickname = nickname;
        this.username = username;
        this.email = email;
    }
}
