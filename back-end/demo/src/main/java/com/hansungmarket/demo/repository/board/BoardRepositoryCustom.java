package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    List<Board> findAllCustom(int page);

    Optional<Board> findByIdCustom(Long id);

    List<Board> findByFieldsCustom(String category, String nickname, String query, int page);

}
