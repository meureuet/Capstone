package com.hansungmarket.demo.dto.user;

import com.hansungmarket.demo.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String nickname;
    private String username;
    private String email;

    public UserDto(User entity) {
        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
    }
}
