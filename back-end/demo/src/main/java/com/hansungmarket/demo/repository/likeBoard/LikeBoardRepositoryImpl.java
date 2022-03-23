package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hansungmarket.demo.entity.board.QBoard.board;
import static com.hansungmarket.demo.entity.board.QLikeBoard.likeBoard;

@RequiredArgsConstructor
@Repository
@Primary
public class LikeBoardRepositoryImpl implements LikeBoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Board> findByUserIdCustom(Long id) {
        return jpaQueryFactory.selectFrom(board)
                .join(likeBoard).on(board.id.eq(likeBoard.board.id), likeBoard.user.id.eq(id))
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByBoardIdAndUserIdCustom(Long boardId, Long userId) {
        Integer fetchOne = jpaQueryFactory.selectOne().from(likeBoard)
                .where(likeBoard.board.id.eq(boardId), likeBoard.user.id.eq(userId))
                .fetchFirst();

        return fetchOne != null;
    }
}
