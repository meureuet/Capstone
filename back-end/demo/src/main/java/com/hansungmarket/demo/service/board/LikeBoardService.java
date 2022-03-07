package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.LikeBoard;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.board.BoardRepository;
import com.hansungmarket.demo.repository.board.LikeBoardRepository;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeBoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;

    // 찜한 게시글 저장
    public void saveLikeBoard(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        LikeBoard likeBoard = LikeBoard.builder()
                .user(user)
                .board(board)
                .build();

        likeBoardRepository.save(likeBoard);
    }

    // 찜한 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByUserId(Long id) {
        List<LikeBoard> likeBoards = likeBoardRepository.findByUserId(id);

        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        for (LikeBoard likeBoard : likeBoards) {
            Board board = likeBoard.getBoard();

            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            boardResponseDtos.add(boardResponseDto);
        }

        return boardResponseDtos;
    }


}
