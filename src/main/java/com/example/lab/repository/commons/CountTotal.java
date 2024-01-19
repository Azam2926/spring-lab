package com.example.lab.repository.commons;

import org.springframework.data.jpa.domain.Specification;

public interface CountTotal<E> {
    Long getTotal();

    Long getTotal(Specification<E> specification);
}
