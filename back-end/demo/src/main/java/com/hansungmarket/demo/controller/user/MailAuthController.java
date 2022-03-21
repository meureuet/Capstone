package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.service.user.MailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MailAuthController {
    private final MailAuthService mailAuthService;

    // 인증메일 보내기
    @PostMapping("/auth/mail")
    public void sendAuthMail(Authentication authentication) throws MessagingException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        mailAuthService.sendAuthMail(principalDetails.getUserId());
    }

    // 토큰 확인
    @GetMapping("/auth/{token}")
    public String verifyToken(@PathVariable String token) {
        mailAuthService.verify(token);
        return "인증 완료";
    }
}
