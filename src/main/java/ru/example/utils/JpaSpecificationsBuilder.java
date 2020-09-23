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

    public Specification<T> buildSpecification(List<SearchCriteria> criteria){
        return (root, query, cb) -> {
            if(criteria == null || criteria.size() == 0){
                return null;
            }

            Predicate result = buildPredicate(root,cb,criteria.get(0));
            for (int i = 1; i < criteria.size(); i++) {
                final SearchCriteria criterion = criteria.get(i);
                if(criterion.getJoinType() == SearchCriteria.JoinType.AND){
                    result = cb.and(result,buildPredicate(root,cb, criterion));
                }
                else{
                    result = cb.or(result,buildPredicate(root,cb,criterion));
                }
            }
            return result;
        };
    }

    public Specification<T> buildAndSpecification(List<SearchCriteria> criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (SearchCriteria criterion : criteria) {
                predicates.add(buildPredicate(root, cb, criterion));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<T> buildOrSpecification(List<SearchCriteria> criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (SearchCriteria criterion : criteria) {
                predicates.add(buildPredicate(root, cb, criterion));
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate buildPredicate(Root<T> root, CriteriaBuilder cb, SearchCriteria criterion) {
        if(criterion.getCriteria() != null && criterion.getCriteria().size() > 0){

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
