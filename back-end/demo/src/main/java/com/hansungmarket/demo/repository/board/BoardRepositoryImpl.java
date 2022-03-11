package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.LikeBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.hansungmarket.demo.entity.board.QBoard.board;

@RequiredArgsConstructor
@Repository
@Primary
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllCustom() {
        return jpaQueryFactory.selectFrom(board)
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public Optional<Board> findByIdCustom(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(board)
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .where(board.id.eq(id))
                .fetchOne());
    }

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
