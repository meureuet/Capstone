package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.likeBoard.LikeBoardRepository;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final UserRepository userRepository;

    // 게시글 찜하기
    @Transactional
    public void saveLikeBoard(String username, Long boardId) {
//        User user = userRepository.findByUsername(username);
//        likeBoardRepository.saveCustom(user.getId(), boardId);
    }

    // 찜한 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByUserId(Long id) {
//        List<LikeBoard> likeBoards = likeBoardRepository.findByUserId(id);
//
//        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
//        for (LikeBoard likeBoard : likeBoards) {
//            Board board = likeBoard.getBoard();
//
//            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
//            boardResponseDtos.add(boardResponseDto);
//        }
//
//        return boardResponseDtos;
        return null;
    }


}
