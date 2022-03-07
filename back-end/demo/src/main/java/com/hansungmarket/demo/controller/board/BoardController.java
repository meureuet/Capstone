package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.dto.board.BoardRequestDto;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.service.board.BoardService;
import com.hansungmarket.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    // 게시글 저장
    @PostMapping("/boards")
    public BoardResponseDto createBoard(@RequestPart(value = "board") @Valid BoardRequestDto requestDto,
                                        @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                        Authentication authentication) throws IOException {
        return boardService.create(requestDto, images, authentication.getName());
    }

    // 게시글 리스트 출력
    @GetMapping("/boards")
    public List<BoardResponseDto> searchAllBoards(@RequestParam(required = false) String category,
                                                  @RequestParam(required = false) String nickname) {
        // 카테고리 검색
        if (!StringUtils.isEmpty(category)) {
            return boardService.searchByCategory(category);
        }
        // 작성자 검색
        else if (!StringUtils.isEmpty(nickname)) {
            Long userId = userService.getUserByNickname(nickname).getId();
            return boardService.searchByUserId(userId);
        }

        // 전체검색
        return boardService.searchAll();
    }

    // id에 해당하는 게시글 출력
    @GetMapping("/boards/{id}")
    public BoardResponseDto searchBoardById(@PathVariable Long id) {
        return boardService.searchById(id);
    }

    // 게시글 수정
    @PutMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @RequestPart(value = "board") BoardRequestDto requestDto,
                                        @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                        Authentication authentication) throws IOException {
        return boardService.updateBoard(id, requestDto, images, authentication.getName());
    }
    
    //게시글 삭제
    @DeleteMapping("/boards/{id}")
    public void deleteBoard(@PathVariable Long id,
                            Authentication authentication) {
        boardService.delete(id, authentication.getName());
    }

}
