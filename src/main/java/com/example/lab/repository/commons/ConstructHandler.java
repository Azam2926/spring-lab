package com.example.lab.repository.commons;

import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface ConstructHandler<E, D> {
    CompoundSelection<D> getConstruct(Root<E> root, CriteriaBuilder cb);
}
