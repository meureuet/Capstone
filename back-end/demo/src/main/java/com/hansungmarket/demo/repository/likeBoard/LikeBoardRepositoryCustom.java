package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.Board;

import java.util.List;

public interface LikeBoardRepositoryCustom {
    List<Board> findByUserIdCustom(Long id);

    Boolean existByBoardIdAndUserIdCustom(Long boardId, Long userId);
}
