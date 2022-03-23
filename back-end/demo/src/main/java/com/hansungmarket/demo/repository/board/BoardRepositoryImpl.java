package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.hansungmarket.demo.entity.board.QBoard.board;

@RequiredArgsConstructor
@Repository
@Primary
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Board> findAllCustom() {
        return jpaQueryFactory.selectFrom(board)
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Board> findByIdCustom(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(board)
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .where(board.id.eq(id))
                .fetchOne());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findByFieldsCustom(String category, String nickname, String contentQuery) {
        return jpaQueryFactory.selectFrom(board)
                .innerJoin(board.user).fetchJoin()
                .where(eqCategory(category),
                        eqNickname(nickname),
                        containsContentQuery(contentQuery))
                .leftJoin(board.boardImages).fetchJoin()
                .distinct()
                .fetch();
    }

    // category 에 값이 있으면 조건식 생성
    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return null;
        }
        return board.goodsCategory.eq(category);
    }

    // nickname 에 값이 있으면 조건식 생성
    private BooleanExpression eqNickname(String nickname) {
        if (StringUtils.isEmpty(nickname)) {
            return null;
        }
        return board.user.nickname.eq(nickname);
    }

    // contentQuery 에 값이 있으면 조건식 생성
    private BooleanExpression containsContentQuery(String contentQuery) {
        if (StringUtils.isEmpty(contentQuery)) {
            return null;
        }
        return board.content.contains(contentQuery);
    }

}
