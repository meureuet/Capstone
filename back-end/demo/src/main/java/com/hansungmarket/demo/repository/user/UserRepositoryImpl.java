package com.hansungmarket.demo.repository.user;

import com.hansungmarket.demo.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.hansungmarket.demo.entity.user.QUser.user;

@RequiredArgsConstructor
@Repository
@Primary
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByUsernameCustom(String username) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .innerJoin(user.role).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne());
    }
}
