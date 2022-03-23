package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.LikeBoard;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.board.BoardRepository;
import com.hansungmarket.demo.repository.likeBoard.LikeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final BoardRepository boardRepository;

    // 게시글 찜하기
    @Transactional
    public Long create(Long boardId, Long userId) {
        // 게시글
        // 게시글이 존재하는지 확인하기 위해 select 쿼리 한번 실행
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 현재 사용자
        User user = User.builder()
                .id(userId)
                .build();

        // 찜 정보
        LikeBoard likeBoard = LikeBoard.builder()
                .board(board)
                .user(user)
                .build();

        // 찜 db에 저장
        return likeBoardRepository.save(likeBoard).getId();
    }

    // 찜한 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByUserId(Long id) {
        List<BoardResponseDto> BoardResponseDtos = likeBoardRepository.findByUserIdCustom(id).stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());

        // 찜 여부 true로 설정
        for (BoardResponseDto boardResponseDto : BoardResponseDtos) {
            boardResponseDto.like();
        }

        return BoardResponseDtos;
    }
    
    // 찜한 게시글 취소
    @Transactional
    public void delete(Long boardId, Long userId) {
        LikeBoard likeBoard = likeBoardRepository.findByBoardIdAndUserId(boardId, userId).orElseThrow(() -> new IllegalArgumentException("찜 내역이 존재하지 않습니다.."));
        likeBoardRepository.delete(likeBoard);
    }
}
