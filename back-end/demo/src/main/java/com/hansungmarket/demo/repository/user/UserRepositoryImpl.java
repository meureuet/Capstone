package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.dto.board.SaleCountDto;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.entity.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.hansungmarket.demo.entity.board.QBoard.board;
import static com.hansungmarket.demo.entity.user.QUser.user;

@RequiredArgsConstructor
@Repository
@Primary
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameCustom(String username) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .innerJoin(user.role).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByUsernameCustom(String username) {
        Integer fetchOne = jpaQueryFactory.selectOne().from(user)
                .where(user.username.eq(username))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByNicknameCustom(String nickname) {
        Integer fetchOne = jpaQueryFactory.selectOne().from(user)
                .where(user.nickname.eq(nickname))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    @Transactional
    public void updateIntroduceCustom(Long id, String introduce) {
        jpaQueryFactory.update(user)
                .where(user.id.eq(id))
                .set(user.introduce, introduce)
                .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findByIdCustom(Long id) {
        return jpaQueryFactory.select(Projections.fields(UserDto.class,
                        user.nickname,
                        user.username,
                        user.email,
                        user.introduce))
                .from(user)
                .where(user.id.eq(id))
                .fetchOne();
    }
}
