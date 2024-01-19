package com.example.lab.repository.commons;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.trimWhitespace;

public class BaseSpecs {

    //region Common specifications
    public static <T> Specification<T> contains(String key, String value) {
        return (root, query, builder) -> likePredicate(root.get(key), value, builder);
    }

    public static <T> Specification<T> equals(String key, Object value) {
        return (root, query, builder) -> equalPredicate(root.get(key), value, builder);
    }

    public static <T> Specification<T> dateFromTo(String key, Date from, Date to) {
        return (root, query, builder) -> predictDateFromTo(root.get(key), from, to, builder);
    }

    public static <T> Specification<T> localDateFromTo(String key, LocalDate from, LocalDate to) {
        return (root, query, builder) -> predictLocalDateFromTo(root.get(key), from, to, builder);
    }

    public static <T> Specification<T> toDate(String key, Date toDate) {
        return (root, query, builder) -> predicateToDate(root.get(key), toDate, builder);
    }

    public static <T> Specification<T> in(String key, Collection<Long> values) {
        return (root, query, builder) -> inPredicate(key, values, root);
    }
    //endregion

    //region Predicates
    public static <T> Predicate inPredicate(String key, Collection<Long> values, Root<T> root) {
        return isEmpty(values) ? null :
                root.get(key).in(values);
    }

    public static Predicate predictDateFromTo(Path<Date> path, Date from, Date to, CriteriaBuilder builder) {
        if (isNull(from) && isNull(to))
            return null;

        if (nonNull(from) && nonNull(to))
            return builder.between(path, from, to);

        if (nonNull(from))
            return builder.greaterThanOrEqualTo(path, from);

        return builder.lessThanOrEqualTo(path, to);
    }

    public static Predicate predictLocalDateFromTo(Path<LocalDate> path, LocalDate from, LocalDate to, CriteriaBuilder builder) {
        if (isNull(from) && isNull(to))
            return null;

        if (nonNull(from) && nonNull(to))
            return builder.between(path, from, to);

        if (nonNull(from))
            return builder.greaterThanOrEqualTo(path, from);

        return builder.lessThanOrEqualTo(path, to);
    }

    public static Predicate predicateToDate(Path<Date> path, Date to, CriteriaBuilder builder) {
        return isNull(to) ? null :
                builder.lessThanOrEqualTo(path, to);
    }

    public static Predicate likePredicate(Expression<String> path, String value, CriteriaBuilder builder) {
        return !hasText(value) ? null :
                builder.like(builder.upper(path), format("%%%s%%", trimWhitespace(value).toUpperCase()));
    }

    public static <T> Predicate equalPredicate(Path<T> path, Object value, CriteriaBuilder builder) {
        return isNull(value) ? null :
                builder.equal(path, value);
    }
    //endregion
}
