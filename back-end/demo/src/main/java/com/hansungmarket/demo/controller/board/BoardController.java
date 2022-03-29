package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.board.BoardRequestDto;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.service.board.BoardService;
import com.hansungmarket.demo.service.board.LikeBoardService;
import com.hansungmarket.demo.service.user.UserService;
import io.swagger.annotations.ApiOperation;
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
    private final LikeBoardService likeBoardService;
    private final UserService userService;

    // 게시글 저장
    @PostMapping("/boards")
    @ApiOperation(value = "게시글 저장", notes = "json에 담겨있는 게시글 정보 + 이미지로 게시글 저장")
    public Long createBoard(@RequestPart(value = "board") @Valid BoardRequestDto requestDto,
                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                            Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.create(requestDto, images, principalDetails.getUserId());
    }

    // 게시글 목록 출력
    @GetMapping("/boards")
    @ApiOperation(value = "게시글 목록 출력", notes = "게시글 목록 출력, 검색 가능")
    public List<BoardResponseDto> searchAllBoards(@RequestParam(required = false) String category,
                                                  @RequestParam(required = false) String nickname,
                                                  @RequestParam(required = false) String query,
                                                  @RequestParam(defaultValue = "1") int page) {

        // 카테고리 검색
        if (category != null || nickname != null || query != null) {
            return boardService.searchByFields(category, nickname, query, page);
        }

        // 전체검색
        return boardService.searchAll(page);
    }

    // id에 해당하는 게시글 출력(게시글 상세보기)
    @GetMapping("/boards/{id}")
    @ApiOperation(value = "게시글 상세보기", notes = "id에 해당하는 게시글 출력")
    public BoardResponseDto searchBoardById(@PathVariable Long id, Authentication authentication) {
        // 로그인 X
        if (authentication == null) {
            return boardService.searchByBoardId(id, null);
        }

        // 로그인 O
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.searchByBoardId(id, principalDetails.getUserId());
    }

    // 게시글 수정
    @PutMapping("/boards/{id}")
    @ApiOperation(value = "게시글 수정", notes = "json에 담겨있는 게시글 정보로 해당 id 게시글 수정")
    public Long updateBoard(@PathVariable Long id,
                            @RequestPart(value = "board") BoardRequestDto requestDto,
                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                            Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.update(id, requestDto, images, principalDetails.getUserId());
    }
    
    //게시글 삭제
    @DeleteMapping("/boards/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "해당 id 게시글 삭제")
    public void deleteBoard(@PathVariable Long id,
                            Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        boardService.delete(id, principalDetails.getUserId());
    }

    // 사용자가 작성한 게시글 출력
    @GetMapping("/myBoards")
    @ApiOperation(value = "작성한 게시글 출력", notes = "사용자가 작성한 게시글 출력")
    public List<BoardResponseDto> getMyBoards(@RequestParam(defaultValue = "1") int page,
                                              Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.searchByFields(null, principalDetails.getNickname(), null, page);
    }

    // 게시글 찜하기
    @PostMapping("/likeBoards/{boardId}")
    @ApiOperation(value = "찜하기", notes = "해당 id를 가진 게시글을 사용자의 찜 목록에 추가")
    public Long createLikeBoard(@PathVariable Long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return likeBoardService.create(boardId, principalDetails.getUserId());
    }

    // 사용자가 찜한 게시글 출력
    @GetMapping("/likeBoards")
    @ApiOperation(value = "찜한 게시글 출력", notes = "사용자가 찜한 게시글 출력")
    public List<BoardResponseDto> getMyLikeBoards(@RequestParam(defaultValue = "1") int page,
                                                  Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return likeBoardService.searchByUserId(principalDetails.getUserId(), page);
    }

    // 게시글 찜하기 취소
    @DeleteMapping("/likeBoards/{boardId}")
    @ApiOperation(value = "찜 취소", notes = "해당 id를 가진 게시글을 사용자의 찜 목록에서 제거")
    public void deleteLikeBoard(@PathVariable Long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        likeBoardService.delete(boardId, principalDetails.getUserId());
    }
}
