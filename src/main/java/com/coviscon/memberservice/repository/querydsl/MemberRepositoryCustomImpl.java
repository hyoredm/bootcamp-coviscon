package com.coviscon.memberservice.repository.querydsl;

import com.coviscon.memberservice.dto.MemberResponseDto;
import com.coviscon.memberservice.dto.QMemberResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.coviscon.memberservice.entity.member.QMember.member;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<MemberResponseDto> searchByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .select(new QMemberResponseDto(
                        member.id.as("memberId"),
                        member.email,
                        member.nickName,
                        member.role.stringValue()
                ))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne());
    }

    @Override
    public String searchEmailByNickName(String nickname) {
        return queryFactory
            .select(member.email)
            .from(member)
            .where(member.nickName.eq(nickname))
            .fetchOne();
    }

    @Override
    public String searchPasswordByEmailAndNickName(String email, String nickname) {
        return queryFactory
            .select(member.password)
            .from(member)
            .where(
                member.email.eq(email),
                member.nickName.eq(nickname)
            )
            .fetchOne();
    }
}
