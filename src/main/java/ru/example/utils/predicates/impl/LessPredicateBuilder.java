package ru.example.utils.predicates.impl;

import ru.example.common.SearchCriteria;
import ru.example.utils.predicates.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class LessPredicateBuilder implements PredicateBuilder {
    @Override
    public SearchCriteria.SearchOperation getManagedOperation() {
        return SearchCriteria.SearchOperation.LESS;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria) {
        if(LocalDateTime.class.equals(path.getJavaType())){
            return cb.lessThan(path,LocalDateTime.parse(criteria.getValue()));
        }
        else {
            return cb.lessThan(path, criteria.getValue());
        }
    }
}
