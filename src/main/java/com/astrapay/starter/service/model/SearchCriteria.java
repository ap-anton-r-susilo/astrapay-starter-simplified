package com.astrapay.starter.service.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T>
 * @author  Raymond Sugiarto
 * @author  Astrapay
 * @version 1.8
 * @since   2021-07-25
 */
@Builder
@Data
@Slf4j
public class SearchCriteria<T> implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    private String key;
    private transient T value;
    private SearchOperation operation;
    private Delimiter delimiter = Delimiter.COMMA;

    public SearchCriteria(String key, T value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public SearchCriteria(String key, T value, SearchOperation operation, Delimiter delimiter) {
        this(key, value, operation);
        this.delimiter = delimiter;
    }

    @AllArgsConstructor
    @Getter
    public enum Delimiter {
        COMMA(","),
        SEMI_COLON(";");

        final String symbol;
    }

    public enum SearchOperation {
        GREATER_THAN,
        LESS_THAN,
        GREATER_THAN_EQUAL,
        LESS_THAN_EQUAL,
        NOT_EQUAL,
        EQUAL,
        IN,
        MATCH,
        MATCH_END,
        EQUAL_IGNORE_CASE;

        public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
            switch (this) {
                case GREATER_THAN:
                case LESS_THAN:
                case GREATER_THAN_EQUAL:
                case LESS_THAN_EQUAL:
                    if (criteria.getValue() instanceof Comparable) {
                        Comparable comparableValue = (Comparable) criteria.getValue();
                        switch (this) {
                            case GREATER_THAN:
                                predicates.add(builder.greaterThan(root.get(criteria.getKey()), comparableValue));
                                break;
                            case LESS_THAN:
                                predicates.add(builder.lessThan(root.get(criteria.getKey()), comparableValue));
                                break;
                            case GREATER_THAN_EQUAL:
                                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), comparableValue));
                                break;
                            case LESS_THAN_EQUAL:
                                predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), comparableValue));
                                break;
                        }
                    }
                    break;
                case NOT_EQUAL:
                    predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case EQUAL:
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case IN:
                    CriteriaBuilder.In<Object> in = builder.in(root.get(criteria.getKey()));
                    if(criteria.getValue() instanceof List) {
                        for (Object val : (List) criteria.getValue()) {
                            in.value(val);
                        }
                    } else {
                        // Deprecated
                        for (String val : criteria.getValue().toString().split(criteria.getDelimiter().getSymbol())) {
                            in.value(val);
                        }
                    }
                    predicates.add(in);
                    break;
                case MATCH:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_END:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case EQUAL_IGNORE_CASE:
                    predicates.add(builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase()));
                    break;
            }
        }
    }
}