package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.service.board.BoardService;
import com.hansungmarket.demo.service.board.LikeBoardService;
import com.hansungmarket.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final BoardService boardService;
    private final LikeBoardService likeBoardService;

    // 회원가입
    @PostMapping("/users")
    public Long signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    // 로그인한 회원정보 출력
    @GetMapping("/users")
    public UserDto getUserDetails(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName());
    }

    // 사용자가 작성한 게시글 출력
    @GetMapping("/users/boards")
    public List<BoardResponseDto> getMyBoards(Authentication authentication) {
        String username = authentication.getName();
        return boardService.searchByUsername(username);
    }

    // 게시글 찜하기
    @PostMapping("/users/likeboards/{boardId}")
    public void likeBoard(@PathVariable Long boardId, Authentication authentication) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        likeBoardService.saveLikeBoard(userId, boardId);
    }

    // 사용자가 찜한 게시글 출력
    @GetMapping("/users/likeboards")
    public List<BoardResponseDto> getMyLikeBoards(Authentication authentication) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return likeBoardService.searchByUserId(userId);
    }

}
