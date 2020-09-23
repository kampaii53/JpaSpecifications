package ru.example.utils.predicates.impl;

import ru.example.common.SearchCriteria;
import ru.example.utils.predicates.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class EqPredicateBuilder implements PredicateBuilder {
    @Override
    public SearchCriteria.SearchOperation getManagedOperation() {
        return SearchCriteria.SearchOperation.EQ;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria) {
        if(criteria.getValue() == null){
            return cb.isNull(path);
        }

        if(LocalDateTime.class.equals(path.getJavaType())){
            return cb.equal(path,LocalDateTime.parse(criteria.getValue()));
        }
        else {
            return cb.equal(path, criteria.getValue());
        }
    }
}
