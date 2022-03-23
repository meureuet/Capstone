package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.service.board.BoardService;
import com.hansungmarket.demo.service.board.LikeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final BoardService boardService;
    private final LikeBoardService likeBoardService;

    // 로그인한 회원정보 출력
    @GetMapping("/users")
    public UserDto getUserDetails(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 세션정보
        return UserDto.builder()
                .nickname(principalDetails.getNickname())
                .username(principalDetails.getUsername())
                .email(principalDetails.getEmail())
                .build();
    }

    // 사용자가 작성한 게시글 출력
    @GetMapping("/users/boards")
    public List<BoardResponseDto> getMyBoards(Authentication authentication) {
        return null;
    }

    // 게시글 찜하기
    @PostMapping("/users/likeBoards/{boardId}")
    public void likeBoard(@PathVariable Long boardId, Authentication authentication) {
        String username = authentication.getName();
        likeBoardService.saveLikeBoard(username, boardId);
    }

    // 사용자가 찜한 게시글 출력
    @GetMapping("/users/likeBoards")
    public List<BoardResponseDto> getMyLikeBoards(Authentication authentication) {
//        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return null;
    }

}
