package com.example.lab.repository;

import com.example.lab.model.Person;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

//    @Query(value = "SELECT p FROM Person p WHERE EXISTS (" +
//            "SELECT 1 FROM unnest(string_to_array(p.ints, ',')) AS value " +
//            "WHERE CAST(value AS integer) < :threshold)")
//    List<Person> findPersonsWithValueLessThanThreshold(@Param("threshold") int threshold);

//    static Specification<Person> hasValueLessThan5() {
//        return (root, query, criteriaBuilder) -> {
//            String[] intValues = root.get("ints").as(String.class).split(",");
//            Expression<Integer> intValue = criteriaBuilder.literal(5);
//
//            for (String value : intValues) {
//                Expression<Integer> arrayValue = criteriaBuilder.literal(Integer.parseInt(value));
//                if (criteriaBuilder.lt(arrayValue, intValue)) {
//                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
//                }
//            }
//            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
//        };
//    }
}
