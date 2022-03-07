package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    List<LikeBoard> findByUserId(Long id);
}
