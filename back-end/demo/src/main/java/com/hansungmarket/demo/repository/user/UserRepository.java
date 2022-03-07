package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByNickname(String nickname);
}
