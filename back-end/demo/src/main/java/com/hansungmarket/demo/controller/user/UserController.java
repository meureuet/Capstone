package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.service.board.BoardService;
import com.hansungmarket.demo.service.board.LikeBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"사용자"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final BoardService boardService;
    private final LikeBoardService likeBoardService;

    // 로그인한 회원정보 출력
    @GetMapping("/users")
    @ApiOperation(value = "회원정보 출력", notes = "현재 로그인한 사용자의 nickname, username, email 출력")
    public UserDto getUserDetails(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 세션정보
        return UserDto.builder()
                .nickname(principalDetails.getNickname())
                .username(principalDetails.getUsername())
                .email(principalDetails.getEmail())
                .build();
    }

}
