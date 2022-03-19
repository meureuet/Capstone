package com.hansungmarket.demo.service.user;

import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.entity.user.Role;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.user.RoleRepository;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final MailAuthService mailAuthService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    // 회원가입
    // form 으로 받는 경우 코드 수정 필요, 현재 json 으로 데이터 받음
    @Transactional
    public Long signUp(SignUpDto signUpDto) {
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(signUpDto.getPassword());
        Role role = Role.builder()
                .id(1L) // ROLE_NOT_VERIFIED 하드코딩, DB 접근 X
                .build();

        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(encodedPassword)
                .nickname(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .role(role)
                .enabled(true)
                .build();

        // 유저 정보 저장(회원가입)
        userRepository.save(user);

        return user.getId();
    }

}
