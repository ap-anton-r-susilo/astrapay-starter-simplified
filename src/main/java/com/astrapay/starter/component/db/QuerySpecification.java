package com.astrapay.starter.component.db;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuerySpecification<T> implements Specification<T> {

    private List<QueryCriteria> searchCriteriaList;
    private Specification<T> specification;

    public QuerySpecification() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public QuerySpecification(Specification<T> specification) {
        this.searchCriteriaList = new ArrayList<>();
        this.specification = specification;
    }

    public void add(QueryCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    public void addGreaterThan(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.GREATER_THAN));
    }

    public void addGreaterThan(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.GREATER_THAN));
    }

    public void addGreaterThanEqual(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.GREATER_THAN_EQUAL));
    }

    public void addGreaterThanEqual(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.GREATER_THAN_EQUAL));
    }

    public void addLessThan(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.LESS_THAN));
    }

    public void addLessThan(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.LESS_THAN));
    }

    public void addLessThanEqual(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.LESS_THAN_EQUAL));
    }

    public void addLessThanEqual(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.LESS_THAN_EQUAL));
    }

    public void addEqual(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.EQUAL));
    }

    public void addEqual(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.EQUAL));
    }

    public void addEqualIgnoreCase(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.EQUAL_IGNORE_CASE));
    }

    public void addEqualIgnoreCase(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.EQUAL_IGNORE_CASE));
    }

    public void addNotEqual(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.NOT_EQUAL));
    }

    public void addNotEqual(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.NOT_EQUAL));
    }

    public void addContains(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.CONTAINS));
    }

    public void addContains(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.CONTAINS));
    }

    public void addStartWith(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.START_WITH));
    }

    public void addStartWith(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.START_WITH));
    }

    public void addEndsWith(List<String> keys, Object value) {
        add(new QueryCriteria(keys, value, QueryCriteria.CriteriaOperation.ENDS_WITH));
    }

    public void addEndsWith(String key, Object value) {
        add(new QueryCriteria(key, value, QueryCriteria.CriteriaOperation.ENDS_WITH));
    }

    public void addIsNull(List<String> keys) {
        add(new QueryCriteria(keys, null, QueryCriteria.CriteriaOperation.IS_NULL));
    }

    public void addIsNull(String key) {
        add(new QueryCriteria(key, null, QueryCriteria.CriteriaOperation.IS_NULL));
    }

    public void addIsNotNull(List<String> keys) {
        add(new QueryCriteria(keys, null, QueryCriteria.CriteriaOperation.IS_NOT_NULL));
    }

    public void addIsNotNull(String key) {
        add(new QueryCriteria(key, null, QueryCriteria.CriteriaOperation.IS_NOT_NULL));
    }

    public void addIsNotEmpty(List<String> keys) {
        add(new QueryCriteria(keys, null, QueryCriteria.CriteriaOperation.IS_NOT_EMPTY));
    }

    public void addIsNotEmpty(String key) {
        add(new QueryCriteria(key, null, QueryCriteria.CriteriaOperation.IS_NOT_EMPTY));
    }

    public void addIsEmpty(List<String> keys) {
        add(new QueryCriteria(keys, null, QueryCriteria.CriteriaOperation.IS_EMPTY));
    }

    public void addIsEmpty(String key) {
        add(new QueryCriteria(key, null, QueryCriteria.CriteriaOperation.IS_EMPTY));
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (this.specification != null) {
            predicates.add(this.specification.toPredicate(root, query, builder));
        }

        searchCriteriaList.forEach(criteria -> {
            predicates.add(
                    criteria.getOperation(builder, root)
            );
        });

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}