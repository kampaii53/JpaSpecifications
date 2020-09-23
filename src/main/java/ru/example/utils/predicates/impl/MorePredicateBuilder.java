package ru.example.utils.predicates.impl;

import org.springframework.stereotype.Component;
import ru.example.common.SearchCriteria;
import ru.example.common.SearchOperation;
import ru.example.utils.predicates.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

@Component
public class MorePredicateBuilder implements PredicateBuilder {
    @Override
    public SearchOperation getManagedOperation() {
        return SearchOperation.MORE;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria) {
        if(LocalDateTime.class.equals(path.getJavaType())){
            return cb.greaterThan(path,LocalDateTime.parse(criteria.getValue()));
        }
        else {
            return cb.greaterThan(path, criteria.getValue());
        }
    }
}
