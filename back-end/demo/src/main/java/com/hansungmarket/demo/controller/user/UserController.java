package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.user.IntroduceDto;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.service.board.BoardService;
import com.hansungmarket.demo.service.board.LikeBoardService;
import com.hansungmarket.demo.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"사용자"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
    private final BoardService boardService;
    private final LikeBoardService likeBoardService;
    private final UserService userService;

    // 로그인한 회원정보 출력
    @GetMapping("/users")
    @ApiOperation(value = "회원정보 출력", notes = "현재 로그인한 사용자의 nickname, username, email, introduce 출력")
    public UserDto getUserDetails(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return userService.getUserInfo(principalDetails.getUserId());
    }

    // 소개글 입력
    @PatchMapping("/users/introduce")
    @ApiOperation(value = "소개글 입력", notes = "사용자 소개글 입력")
    public void updateIntroduce(@RequestBody IntroduceDto introduceDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        userService.updateIntroduce(principalDetails.getUserId(), introduceDto.getIntroduce());
    }
}
