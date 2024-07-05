package com.sparta.outsorcingproject.plustask;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsorcingproject.entity.Like;
import com.sparta.outsorcingproject.entity.LikeTypeEnum;
import com.sparta.outsorcingproject.entity.QLike;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class LikeDslRepository extends CustomJPARepository {

    public Like findByUserIdAndTypeId(Long userId, LikeTypeEnum likeTypeEnum, Long typeId){
        QLike qLike = QLike.like;

        Like like = null;
        if(likeTypeEnum == LikeTypeEnum.Store) {
            like = jqf.selectFrom(qLike)
                    .where(qLike.user.id.eq(userId).and(qLike.store.id.eq(typeId)))
                    .fetchOne();
        }
        else if(likeTypeEnum == LikeTypeEnum.Review) {
            like = jqf.selectFrom(qLike)
                    .where(qLike.user.id.eq(userId).and(qLike.review.id.eq(typeId)))
                    .fetchOne();
        }

        return like;
    }

    public List<Like> findAllByUserIdAndTypeId(Long userId, LikeTypeEnum likeTypeEnum){
        QLike qLike = QLike.like;

        List<Like> likes = jqf.selectFrom(qLike)
                .where(qLike.user.id.eq(userId).and(qLike.type.eq(likeTypeEnum)))
                .fetch();

        return likes;
    }

    public Page<Like> findAllByUserIdAndTypeId(Pageable pageable, Long userId, LikeTypeEnum likeTypeEnum){
        QLike qLike = QLike.like;

        List<Like> likes = jqf.selectFrom(qLike)
                .where(qLike.user.id.eq(userId).and(qLike.type.eq(likeTypeEnum)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Like> count = jqf
                .select(qLike)
                .from(qLike)
                .where(qLike.user.id.eq(userId).and(qLike.type.eq(likeTypeEnum)));

        return PageableExecutionUtils.getPage(likes, pageable, count::fetchCount);
    }

    public void delete(Like like){
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QLike qLike = QLike.like;

        jqf.delete(qLike)
                .where(qLike.id.eq(like.getId()))
                .execute();
    }



    public long findLikeCount(Long userId, LikeTypeEnum likeTypeEnum){
        QLike qLike = QLike.like;

        long likeCount = jqf.select(qLike.count())
                    .from(qLike)
                    .where(qLike.user.id.eq(userId).and(qLike.type.eq(likeTypeEnum)))
                    .fetchOne();

        return likeCount;
    }

    /*public List<Like> selectPersonByNm(String firstNm, String lastNm){
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QLike person = QLike.like;

        List<Like> personList = jqf
                .selectFrom(person)
                .where(person.firstName.eq(firstNm)
                        .and(person.lastName.eq(lastNm))
                        .fetch();

        return personList;
    }*/
}
