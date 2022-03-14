package com.hansungmarket.demo.service.user;

import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.user.RoleRepository;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    // 회원가입
    @Transactional
    public Long signUp(SignUpDto signUpDto) {
        User user = signUpDto.toEntity();
        user.setRole(roleRepository.findByRoleName("ROLE_USER"));
        user.setEnabled(true);

        // 유저 정보 저장(회원가입)
        userRepository.save(user);

        return user.getId();
    }
}
