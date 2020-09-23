package ru.example.utils;

import org.springframework.data.jpa.domain.Specification;
import ru.example.common.SearchCriteria;
import ru.example.utils.predicates.PredicateBuilder;
import ru.example.utils.predicates.impl.*;

import javax.persistence.criteria.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JpaSpecificationsBuilder<T> {

    private Map<SearchCriteria.SearchOperation, PredicateBuilder> predicateBuilders = Stream.of(
            new AbstractMap.SimpleEntry<SearchCriteria.SearchOperation,PredicateBuilder>(SearchCriteria.SearchOperation.EQ,new EqPredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchCriteria.SearchOperation,PredicateBuilder>(SearchCriteria.SearchOperation.MORE,new MorePredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchCriteria.SearchOperation,PredicateBuilder>(SearchCriteria.SearchOperation.MOREQ,new MoreqPredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchCriteria.SearchOperation,PredicateBuilder>(SearchCriteria.SearchOperation.LESS,new LessPredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchCriteria.SearchOperation,PredicateBuilder>(SearchCriteria.SearchOperation.LESSEQ,new LesseqPredicateBuilder())
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public Specification<T> buildSpecification(SearchCriteria criterion){
        return (root, query, cb) -> buildPredicate(root,cb,criterion);
    }

    private Predicate buildPredicate(Root<T> root, CriteriaBuilder cb, SearchCriteria criterion) {
        if(criterion.isComplex()){
            List<Predicate> predicates = new ArrayList<>();
            for (SearchCriteria subCriterion : criterion.getCriteria()) {
                predicates.add(buildPredicate(root,cb,subCriterion));
            }
            if(SearchCriteria.JoinType.AND.equals(criterion.getJoinType())){
                return cb.and(predicates.toArray(new Predicate[0]));
            }
            else{
                return cb.or(predicates.toArray(new Predicate[0]));
            }
        }
        return predicateBuilders.get(criterion.getOperation()).getPredicate(cb,buildPath(root, criterion.getKey()),criterion);
    }

    private Path buildPath(Root<T> root, String key) {

        if (!key.contains(".")) {
            return root.get(key);
        } else {
            String[] path = key.split("\\.");

            Join<Object, Object> join = root.join(path[0]);
            for (int i = 1; i < path.length - 1; i++) {
                join = join.join(path[i]);
            }
            return join.get(path[path.length - 1]);
        }

    }

}
