package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.LikeBoard;

import java.util.List;

public interface LikeBoardRepositoryCustom {
    List<Board> findByUsernameCustom(String username);
}
