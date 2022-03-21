package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
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
    public Long createBoard(@RequestPart(value = "board") @Valid BoardRequestDto requestDto,
                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                            Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.create(requestDto, images, principalDetails.getUserId());
    }

    // 게시글 리스트 출력
    @GetMapping("/boards")
    public List<BoardResponseDto> searchAllBoards(@RequestParam(required = false) String category,
                                                  @RequestParam(required = false) String nickname,
                                                  @RequestParam(required = false) String contentQuery) {
        // 카테고리 검색
        if (category != null || nickname != null || contentQuery != null) {
            return boardService.searchByFields(category, nickname, contentQuery);
        }

        // 전체검색
        return boardService.searchAll();
    }

    // id에 해당하는 게시글 출력
    @GetMapping("/boards/{id}")
    public BoardResponseDto searchBoardById(@PathVariable Long id) {
        return boardService.searchByBoardId(id);
    }

    // 게시글 수정
    @PutMapping("/boards/{id}")
    public Long updateBoard(@PathVariable Long id,
                            @RequestPart(value = "board") BoardRequestDto requestDto,
                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                            Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.update(id, requestDto, images, principalDetails.getUserId());
    }
    
    //게시글 삭제
    @DeleteMapping("/boards/{id}")
    public void deleteBoard(@PathVariable Long id,
                            Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        boardService.delete(id, principalDetails.getUserId());
    }

}
