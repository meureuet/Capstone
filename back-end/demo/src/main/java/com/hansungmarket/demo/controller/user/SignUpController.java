package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/signUp/checkUsername/{username}")
    public Boolean checkUsername(@PathVariable String username) {
        return userService.checkDuplicateUsername(username);
    }

    // nickname 중복검사
    @GetMapping("/signUp/checkNickname/{nickname}")
    public Boolean checkNickname(@PathVariable String nickname) {
        return userService.checkDuplicateNickname(nickname);
    }
}
