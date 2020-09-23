package ru.example.utils.predicates.impl;

import ru.example.common.SearchCriteria;
import ru.example.utils.predicates.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class LesseqPredicateBuilder implements PredicateBuilder {
    @Override
    public SearchCriteria.SearchOperation getManagedOperation() {
        return SearchCriteria.SearchOperation.LESSEQ;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria) {
        if(LocalDateTime.class.equals(path.getJavaType())){
            return cb.lessThanOrEqualTo(path,LocalDateTime.parse(criteria.getValue()));
        }
        else {
            return cb.lessThanOrEqualTo(path, criteria.getValue());
        }
    }
}
