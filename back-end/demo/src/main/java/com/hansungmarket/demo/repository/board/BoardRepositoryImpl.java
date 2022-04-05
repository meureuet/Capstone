package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.Board;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Optional<Board> findByIdCustom(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(board)
                .where(board.id.eq(id))
                .innerJoin(board.user).fetchJoin()
                .leftJoin(board.boardImages).fetchJoin()
                .fetchOne());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findByFieldsCustom(String category, String nickname, String goodsName,
                                          String title, Boolean sale, String orderType, int page) {
        long pageSize = 10L;

        // offset 설정을 위해 -1
        page--;

        // 페이징에 필요한 board id만 추출출
        List<Long> ids = jpaQueryFactory.select(board.id).from(board)
                .where(eqCategory(category),
                        eqNickname(nickname),
                        containsGoodsName(goodsName),
                        containsTitle(title),
                        eqSale(sale))
                .orderBy(board.createdDateTime.desc())
                .offset(page * pageSize)
                .limit(pageSize)
                .fetch();

        // ids 가 비어있으면 바로 리턴
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        // ids 에 존재하는 값만 select
        return jpaQueryFactory.selectFrom(board)
                .where(board.id.in(ids))
                .innerJoin(board.user).fetchJoin()
                .orderBy(board.createdDateTime.desc())
                .leftJoin(board.boardImages).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public Optional<Long> findUserIdByIdCustom(Long id) {
        return Optional.ofNullable(jpaQueryFactory.select(board.user.id).from(board)
                .where(board.id.eq(id))
                .fetchOne());
    }

    @Override
    public void updateSaleCustom(Long id, Boolean sale) {
        jpaQueryFactory.update(board)
                .where(board.id.eq(id))
                .set(board.sale, sale)
                .execute();
    }

    // orderType 에 따라 에 쿼리 생성
    private OrderSpecifier<LocalDateTime> orderType(String orderType) {
        if (orderType.equals("latest")) {

        } else {

        }
        return null;
    }

    // category 에 값이 있으면 조건식 생성
    // 카테고리 검색
    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return null;
        }
        return board.goodsCategory.eq(category);
    }

    // nickname 에 값이 있으면 조건식 생성
    // 닉네임 검색
    private BooleanExpression eqNickname(String nickname) {
        if (StringUtils.isEmpty(nickname)) {
            return null;
        }
        return board.user.nickname.eq(nickname);
    }

    // goodsName 에 값이 있으면 조건식 생성
    // 제목으로 검색
    private BooleanExpression containsGoodsName(String goodsName) {
        if (StringUtils.isEmpty(goodsName)) {
            return null;
        }
        return board.goodsName.contains(goodsName);
    }

    // title 에 값이 있으면 조건식 생성
    // 제목으로 검색
    private BooleanExpression containsTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        return board.title.contains(title);
    }

    // sale 값에 따라 조건식 생성
    // 판매상품, 판매완료상품, 전체상품 검색
    private BooleanExpression eqSale(Boolean sale) {
        if (sale == null) {
            return null;
        }

        if (sale) {
            return board.sale.eq(true);
        } else {
            return board.sale.eq(false);
        }
    }

}
