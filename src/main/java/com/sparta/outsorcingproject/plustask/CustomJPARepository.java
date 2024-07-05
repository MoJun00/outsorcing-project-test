package com.sparta.outsorcingproject.plustask;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomJPARepository {
    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory jqf;

    public <T> void save(T entity){
        em.persist(entity);
    }
}
