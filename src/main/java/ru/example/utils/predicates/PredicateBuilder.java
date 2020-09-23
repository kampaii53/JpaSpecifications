package ru.example.utils.predicates;

import ru.example.common.SearchCriteria;
import ru.example.common.SearchOperation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public interface PredicateBuilder {

    SearchOperation getManagedOperation();

    Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria);
}
