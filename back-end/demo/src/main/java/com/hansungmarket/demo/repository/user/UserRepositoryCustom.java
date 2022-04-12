package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUsernameCustom(String username);

    Boolean existByUsernameCustom(String username);

    Boolean existByNicknameCustom(String nickname);

    void updateIntroduceCustom(Long id, String introduce);

    UserDto findUserDtoByIdCustom(Long id);

    List<UserDto> findUserDtoListByIdCustom(List<Long> ids);
}
