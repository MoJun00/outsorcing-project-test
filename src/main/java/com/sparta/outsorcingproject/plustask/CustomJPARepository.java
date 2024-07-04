package com.sparta.outsorcingproject.plustask;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomJPARepository {
    @PersistenceContext
    EntityManager em;
    JPAQueryFactory jqf;

    public CustomJPARepository() {
        jqf = new JPAQueryFactory(em);
    }

    public <T> void save(T entity){
        em.persist(entity);
    }
}
