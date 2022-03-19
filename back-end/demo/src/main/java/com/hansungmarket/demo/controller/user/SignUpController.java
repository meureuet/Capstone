package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignUpController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signUp")
    public Long signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    // username 중복검사
    @PostMapping("/signUp/checkUsername")
    public Boolean checkUsername(@RequestBody String username) {
        return null;
    }

    // nickname 중복검사
    @PostMapping("/signUp/checkNickname")
    public Boolean checkNickname(@RequestBody String nickname) {
        return null;
    }
}
