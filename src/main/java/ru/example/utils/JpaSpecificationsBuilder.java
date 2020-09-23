package ru.example.utils;

import org.springframework.data.jpa.domain.Specification;
import ru.example.common.JoinType;
import ru.example.common.SearchCriteria;
import ru.example.common.SearchOperation;
import ru.example.utils.predicates.PredicateBuilder;
import ru.example.utils.predicates.impl.*;

import javax.persistence.criteria.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The thing to build specifications for complex JPA requests
 * @param <T> the entity you build request on
 * @author kampaii
 */
public class JpaSpecificationsBuilder<T> {

    private Map<SearchOperation, PredicateBuilder> predicateBuilders = Stream.of(
            new AbstractMap.SimpleEntry<SearchOperation,PredicateBuilder>(SearchOperation.EQ,new EqPredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchOperation,PredicateBuilder>(SearchOperation.MORE,new MorePredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchOperation,PredicateBuilder>(SearchOperation.MOREQ,new MoreqPredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchOperation,PredicateBuilder>(SearchOperation.LESS,new LessPredicateBuilder()),
            new AbstractMap.SimpleEntry<SearchOperation,PredicateBuilder>(SearchOperation.LESSEQ,new LesseqPredicateBuilder())
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    /**
     * builds JPA specification based on criteria you make
     * @param criterion tree of criteria
     * @return specification, which you can then pass to repository
     */
    public Specification<T> buildSpecification(SearchCriteria criterion){
        return (root, query, cb) -> buildPredicate(root,cb,criterion);
    }

    /**
     * merges your specification to one in jointype manner
     * @param specifications your collection
     * @param joinType how to merge collection items
     * @return merged specification
     */
    public Specification<T> mergeSpecifications(List<Specification> specifications, JoinType joinType) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            specifications.forEach(specification -> predicates.add(specification.toPredicate(root, query, cb)));

            if(joinType.equals(JoinType.AND)){
                return cb.and(predicates.toArray(new Predicate[0]));
            }
            else{
                return cb.or(predicates.toArray(new Predicate[0]));
            }

        };
    }

    /**
     * recursively builds predicates based on passed criterion
     */
    private Predicate buildPredicate(Root<T> root, CriteriaBuilder cb, SearchCriteria criterion) {
        if(criterion.isComplex()){
            List<Predicate> predicates = new ArrayList<>();
            for (SearchCriteria subCriterion : criterion.getCriteria()) {
                // TODO add recursion limit
                predicates.add(buildPredicate(root,cb,subCriterion));
            }
            if(JoinType.AND.equals(criterion.getJoinType())){
                return cb.and(predicates.toArray(new Predicate[0]));
            }
            else{
                return cb.or(predicates.toArray(new Predicate[0]));
            }
        }
        return predicateBuilders.get(criterion.getOperation()).getPredicate(cb,buildPath(root, criterion.getKey()),criterion);
    }

    /**
     * builds path to the endpoint field of entity
     */
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
