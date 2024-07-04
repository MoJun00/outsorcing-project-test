package com.sparta.outsorcingproject.plustask;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsorcingproject.entity.Like;
import com.sparta.outsorcingproject.entity.LikeTypeEnum;
import com.sparta.outsorcingproject.entity.QLike;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDslRepository extends CustomJPARepository {

    public Like findByUserIdAndTypeId(Long userId, LikeTypeEnum likeTypeEnum, Long typeId){
        JPAQueryFactory jqf = new JPAQueryFactory(em);
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
    public void delete(Like like){
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QLike qLike = QLike.like;

        jqf.delete(qLike)
                .where(qLike.id.eq(like.getId()))
                .execute();
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
