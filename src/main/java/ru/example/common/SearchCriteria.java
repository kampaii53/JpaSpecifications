package ru.example.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public class SearchCriteria {

    private String key;

    /**
     * Оператор сравнения
     */
    public enum SearchOperation {

        MORE("MORE"),

        LESS("LESS"),

        EQ("EQ"),

        MOREQ("MOREQ"),

        LESSEQ("LESSEQ");

        private String value;

        SearchOperation(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        /**
         * Получить значение енума из строки
         *
         * @param text строковое представление значения енума
         * @return значение енума
         */
        @JsonCreator
        public static SearchCriteria.SearchOperation fromValue(String text) {
            return Arrays.stream(SearchOperation.values())
                    .filter(candidate -> candidate.value.equals(text))
                    .findFirst()
                    .orElse(null);
        }
    }

    private SearchOperation operation;

    private String value;

    private List<SearchCriteria> criteria;

    /**
     * Тип соединения с условием
     */
    public enum JoinType {

        AND("AND"),

        OR("OR");

        private String value;

        JoinType(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        /**
         * Получить значение енума из строки
         *
         * @param text строковое представление значения енума
         * @return значение енума
         */
        @JsonCreator
        public static JoinType fromValue(String text) {
            return Arrays.stream(JoinType.values())
                    .filter(candidate -> candidate.value.equals(text))
                    .findFirst()
                    .orElse(null);
        }
    }

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
