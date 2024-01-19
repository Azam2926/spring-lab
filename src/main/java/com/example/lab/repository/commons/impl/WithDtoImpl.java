package com.example.lab.repository.commons.impl;

import com.example.lab.repository.commons.ConstructHandler;
import com.example.lab.repository.commons.PropertyHandler;
import com.example.lab.repository.commons.WithDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class WithDtoImpl<E, D> extends CountTotalImpl<E> implements WithDto<E, D> {
    protected final Logger log = LogManager.getLogger(this.getClass());
    private final EntityManager em;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;
    private final CriteriaBuilder cb;

    public WithDtoImpl(EntityManager em, Class<E> entityClass, Class<D> dtoClass) {
        super(em, entityClass);
        this.em = em;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        cb = em.getCriteriaBuilder();
    }

    //region Alias functions
    @Override
    public Collection<D> findAllDto() {
        return findAllDto(where(null));
    }

    @Override
    public Collection<D> findAllDto(@Nullable Specification<E> specification) {
        return findAllDto(specification, this::getConstruct);
    }

    @Override
    public Page<D> findAllDto(Pageable pageable) {
        return findAllDto(where(null), pageable);
    }

    @Override
    public Page<D> findAllDto(Specification<E> specification, Pageable pageable) {
        return findAllDto(specification, pageable, this::propertyHandler);
    }

    @Override
    public Page<D> findAllDto(Specification<E> specification, Pageable pageable, PropertyHandler<E> propertyHandler) {
        return findAllDto(specification, pageable, propertyHandler, this::getConstruct);
    }

    @Override
    public Page<D> findAllDto(Specification<E> specification, Pageable pageable, ConstructHandler<E, D> propertyHandler) {
        return findAllDto(specification, pageable, this::propertyHandler, propertyHandler);
    }

    //endregion

    //region Base functions
    public Page<D> findAllDto(Specification<E> specification, Pageable pageable, PropertyHandler<E> propertyHandler, ConstructHandler<E, D> constructHandler) {
        CriteriaQuery<D> cqr = cb.createQuery(dtoClass);
        Root<E> root = cqr.from(entityClass);
        CompoundSelection<D> construct = constructHandler.getConstruct(root, cb);

        // order by
        ArrayList<Order> orderList = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            Collection<Expression<?>> expression = propertyHandler.propertyToExpression(order.getProperty(), root);
            for (Expression<?> exp : expression) {

                if (order.isAscending()) orderList.add(cb.asc(exp));
                else orderList.add(cb.desc(exp));
            }
        }

        if (orderList.isEmpty()) orderList.add(cb.desc(root.get("id")));

        Predicate predicate = specification.toPredicate(root, cqr, cb);
        if (predicate != null) cqr.where(predicate);

        cqr.select(construct).orderBy(orderList);

        // pagination
        TypedQuery<D> typedQuery = em.createQuery(cqr);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setHint("org.hibernate.cacheable", true);
        typedQuery.setHint("org.hibernate.cacheMode", "NORMAL");
        typedQuery.setHint("org.hibernate.readOnly", true);
        typedQuery.setHint("org.hibernate.comment", "findAllDto");
        typedQuery.setHint("org.hibernate.fetchSize", pageable.getPageSize());
        List<D> result = typedQuery.getResultList();

        Long total = getTotal(specification);

        log.info("total elements: {}, {}", result.size(), pageable);

        return new PageImpl<>(result, pageable, total);
    }

    public Collection<D> findAllDto(@Nullable Specification<E> specification, ConstructHandler<E, D> constructHandler) {
        CriteriaQuery<D> cqr = cb.createQuery(dtoClass);
        Root<E> root = cqr.from(entityClass);
        cqr.select(constructHandler.getConstruct(root, cb));

        if (specification != null) {
            Predicate predicate = specification.toPredicate(root, cqr, cb);
            if (predicate != null) cqr.where(predicate);
        }

        TypedQuery<D> typedQuery = em.createQuery(cqr);
        List<D> result = typedQuery.getResultList();

        log.info("total elements: {}", result.size());
        return result;
    }
    //endregion

    //region Utils

    public Collection<Expression<?>> propertyHandler(String property, Root<E> root) {
        return List.of(root.get(property));
    }

    private CompoundSelection<D> getConstruct(Root<E> root, CriteriaBuilder cb) {
        return cb.construct(dtoClass, root);
    }
    //endregion

}
