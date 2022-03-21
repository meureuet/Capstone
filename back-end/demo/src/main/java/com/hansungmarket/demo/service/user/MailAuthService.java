package com.hansungmarket.demo.service.user;

import com.hansungmarket.demo.entity.user.Role;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MailAuthService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${address}")
    private String address;

    // 인증메일 전송
    @Async
    @Transactional
    public void sendAuthMail(Long userId) throws MessagingException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 랜덤값으로 토큰 생성
        String token = UUID.randomUUID().toString();
        String link = address + "/api/auth/" + token;
        // 토큰 업데이트
        user.setAuthToken(token);

        // 이메일 내용 가져오기
        Context context = new Context();
        context.setVariable("link", link);
        String mailContent = templateEngine.process("mailTemplate", context);

        // 이메일 설정
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject("한성마켓 이메일 인증");
        messageHelper.setText(mailContent, true);

        // 이메일 전송
        mailSender.send(mimeMessage);
    }

    // 계정 인증
    @Transactional
    public void verify(String token) {
        User user = userRepository.findByAuthToken(token).orElseThrow(() -> new IllegalArgumentException("인증 가능한 계정이 존재하지 않습니다."));

        Role role = Role.builder()
                .id(2L) // ROLE_USER 하드코딩, DB 접근 X
                .build();

        // ROLE_USER 권한 설정
        user.setRole(role);
    }
}
