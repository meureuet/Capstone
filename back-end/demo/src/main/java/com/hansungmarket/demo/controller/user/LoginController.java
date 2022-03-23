package com.hansungmarket.demo.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {
    // ex) 403 forbidden 으로 리턴값 수정

    @GetMapping("/login")
    public Boolean noLogin() {
        return false;
    }

    @GetMapping("/login/success")
    public Boolean successLogin() {
        return true;
    }

    @GetMapping("/login/fail")
    public Boolean failLogin() {
        return false;
    }

    @GetMapping("/logout/success")
    public void successLogout() {}
}
