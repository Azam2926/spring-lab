package com.example.lab.repository.commons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.Collection;

public interface WithDto<E, D> extends CountTotal<E> {
    Collection<D> findAllDto();

    Page<D> findAllDto(Pageable pageable);

    Collection<D> findAllDto(Specification<E> specification);

    Collection<D> findAllDto(@Nullable Specification<E> specification, ConstructHandler<E, D> constructHandler);

    Page<D> findAllDto(Specification<E> specification, Pageable pageable);

    Page<D> findAllDto(Specification<E> specification, Pageable pageable, PropertyHandler<E> orderHandler);

    Page<D> findAllDto(Specification<E> specification, Pageable pageable, ConstructHandler<E, D> propertyHandler);

    Page<D> findAllDto(Specification<E> specification, Pageable pageable, PropertyHandler<E> propertyHandler, ConstructHandler<E, D> constructHandler);

}
