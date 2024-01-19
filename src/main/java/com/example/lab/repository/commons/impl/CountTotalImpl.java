package com.example.lab.repository.commons.impl;

import com.example.lab.repository.commons.CountTotal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.Collections;

import static org.springframework.data.jpa.domain.Specification.where;

public class CountTotalImpl<E> implements CountTotal<E> {
    private final EntityManager em;
    private final Class<E> entityClass;
    private final CriteriaBuilder cb;

    public CountTotalImpl(EntityManager em, Class<E> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
        cb = em.getCriteriaBuilder();
    }

    @Override
    public Long getTotal() {
        return getTotal(where(null));
    }

    @Override
    public Long getTotal(@Nullable Specification<E> specification) {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<E> root = query.from(entityClass);
        query.select(cb.count(root));

        Predicate predicate = specification != null ? specification.toPredicate(root, query, cb) : null;
        if (predicate != null)
            query.where(predicate);

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.emptyList());

        TypedQuery<Long> typedQuery = em.createQuery(query);
        return typedQuery.getSingleResult();
    }
}
