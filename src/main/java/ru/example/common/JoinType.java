package ru.example.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

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
