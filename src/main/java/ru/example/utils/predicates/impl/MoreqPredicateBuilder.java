package ru.example.utils.predicates.impl;

import ru.example.common.SearchCriteria;
import ru.example.common.SearchOperation;
import ru.example.utils.predicates.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class MoreqPredicateBuilder implements PredicateBuilder {
    @Override
    public SearchOperation getManagedOperation() {
        return SearchOperation.MOREQ;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria) {
        if(LocalDateTime.class.equals(path.getJavaType())){
            return cb.greaterThanOrEqualTo(path,LocalDateTime.parse(criteria.getValue()));
        }
        else {
            return cb.greaterThanOrEqualTo(path, criteria.getValue());
        }
    }
}
