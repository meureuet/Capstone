package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long>, LikeBoardRepositoryCustom {
    // querydsl, jpql 에 insert 존재하지 않음
    // native query 사용
    @Query(value = "insert into like_board(board_id, user_id values (:userId, :boardId)", nativeQuery = true)
    @Modifying
    LikeBoard saveCustom(@Param("userId") Long userId, @Param("boardId") Long boardId);
}
