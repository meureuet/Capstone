package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hansungmarket.demo.entity.board.QBoard.board;

@RequiredArgsConstructor
@Repository
@Primary
public class LikeBoardRepositoryImpl implements LikeBoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findByUsernameCustom(String username) {
        return jpaQueryFactory.selectFrom(board)
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .where(board.user.username.eq(username))
                .distinct()
                .fetch();
    }

}
