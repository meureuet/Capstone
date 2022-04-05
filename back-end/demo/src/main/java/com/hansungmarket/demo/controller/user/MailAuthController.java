package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.service.user.MailAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Api(tags = {"메일인증"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MailAuthController {
    private final MailAuthService mailAuthService;

    // 인증메일 보내기
    @PostMapping("/auth/mail")
    @ApiOperation(value = "인증메일 보내기", notes = "인증 토큰이 담긴 메일을 사용자의 이메일 주소로 발송")
    public void sendAuthMail(Authentication authentication) throws MessagingException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        mailAuthService.sendAuthMail(principalDetails.getUserId());
    }

    // 토큰 확인
    @GetMapping("/auth/{token}")
    @ApiOperation(value = "인증메일 확인", notes = "메일로 인증하면 계정 권한 상승(글쓰기 등 가능)")
    public String verifyToken(@PathVariable String token) {
        mailAuthService.verify(token);
        return "인증 완료";
    }
}
