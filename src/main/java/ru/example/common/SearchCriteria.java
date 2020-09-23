package ru.example.common;

import java.util.List;

public class SearchCriteria {

    private String key;

    private SearchOperation operation;

    private String value;

    private List<SearchCriteria> criteria;

    private JoinType joinType;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, SearchOperation operation, String value, List<SearchCriteria> criteria, JoinType joinType) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.criteria = criteria;
        this.joinType = joinType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SearchCriteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<SearchCriteria> criteria) {
        this.criteria = criteria;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public boolean isComplex(){
        return this.joinType != null && this.criteria != null && this.criteria.size() > 0;
    }
}
