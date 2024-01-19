package com.example.lab.repository.commons;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import java.util.Collection;

@FunctionalInterface
public interface PropertyHandler<E> {
    Collection<Expression<?>> propertyToExpression(String property, Root<E> root);
}
