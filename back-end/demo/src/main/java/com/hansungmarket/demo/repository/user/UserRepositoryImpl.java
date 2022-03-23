package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
