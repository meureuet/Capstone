package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long>, LikeBoardRepositoryCustom {
    Optional<LikeBoard> findByBoardIdAndUserId(Long boardId, Long UserId);
}
