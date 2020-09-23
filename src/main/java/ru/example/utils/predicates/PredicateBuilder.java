package ru.example.utils.predicates;

import ru.example.common.SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public interface PredicateBuilder {

    SearchCriteria.SearchOperation getManagedOperation();

    Predicate getPredicate(CriteriaBuilder cb, Path path, SearchCriteria criteria);
}
