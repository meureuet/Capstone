package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByGoodsCategory(String goodsCategory);

    List<Board> findByUserId(Long id);
}
