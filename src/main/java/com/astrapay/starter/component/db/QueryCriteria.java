package com.astrapay.starter.component.db;

import lombok.Data;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class QueryCriteria implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;
    private List<String> keyList;
    private Object value;
    private CriteriaOperation criteriaOperation;

    public QueryCriteria(String key, Object value, CriteriaOperation criteriaOperation) {
        this.value = value;
        this.criteriaOperation = criteriaOperation;
        String[] splitKeys = key.split("\\.");
        keyList = Arrays.asList(splitKeys);
    }

    public QueryCriteria(List<String> keys, Object value, CriteriaOperation criteriaOperation) {
        this.value = value;
        this.criteriaOperation = criteriaOperation;
        this.keyList = keys;
    }

    private Path<?> buildKeyName(Root<?> root) {
        if (keyList.size() > 1) {
            var lastIndex = keyList.size()-1;
            var pathJoin = root.join(keyList.get(0));
            for(int i=1; i<lastIndex; i++) {
                pathJoin = pathJoin.join(keyList.get(i));
            }
            return pathJoin.get(keyList.get(lastIndex));
        } else {
            return root.get(keyList.get(0));
        }

    }

    public Predicate getOperation(CriteriaBuilder builder, Root<?> root) {
        var path = buildKeyName( root);
        return criteriaOperation.toPredicate(builder, path, value);
    }

    public enum CriteriaOperation {
        GREATER_THAN {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                if ( value instanceof Integer ) {
                    return builder.greaterThan(path.as(Integer.class), (Integer)value);
                } else if (value instanceof Long) {
                    return builder.greaterThan(path.as(Long.class), (Long)value);
                } else if (value instanceof Double) {
                    return builder.greaterThan(path.as(Double.class), (Double) value);
                } else if (value instanceof Float) {
                    return builder.greaterThan(path.as(Float.class), (Float) value);
                } else {
                    return builder.greaterThan(path.as(String.class), (String)value);
                }
            }
        },
        GREATER_THAN_EQUAL {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                if ( value instanceof Integer ) {
                    return builder.greaterThanOrEqualTo(path.as(Integer.class), (Integer)value);
                } else if (value instanceof Long) {
                    return builder.greaterThanOrEqualTo(path.as(Long.class), (Long)value);
                } else if (value instanceof Double) {
                    return builder.greaterThanOrEqualTo(path.as(Double.class), (Double) value);
                } else if (value instanceof Float) {
                    return builder.greaterThan(path.as(Float.class), (Float) value);
                } else {
                    return builder.greaterThanOrEqualTo(path.as(String.class), (String)value);
                }
            }
        },
        LESS_THAN {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                if ( value instanceof Integer ) {
                    return builder.lessThan(path.as(Integer.class), (Integer)value);
                } else if (value instanceof Long) {
                    return builder.lessThan(path.as(Long.class), (Long)value);
                } else if (value instanceof Double) {
                    return builder.lessThan(path.as(Double.class), (Double) value);
                } else if (value instanceof Float) {
                    return builder.greaterThan(path.as(Float.class), (Float) value);
                } else {
                    return builder.lessThan(path.as(String.class), (String)value);
                }
            }
        },
        LESS_THAN_EQUAL {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                if ( value instanceof Integer ) {
                    return builder.lessThanOrEqualTo(path.as(Integer.class), (Integer)value);
                } else if (value instanceof Long) {
                    return builder.lessThanOrEqualTo(path.as(Long.class), (Long)value);
                } else if (value instanceof Double) {
                    return builder.lessThanOrEqualTo(path.as(Double.class), (Double) value);
                } else if (value instanceof Float) {
                    return builder.greaterThan(path.as(Float.class), (Float) value);
                } else {
                    return builder.lessThanOrEqualTo(path.as(String.class), (String)value);
                }
            }
        },
        EQUAL {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                return builder.equal(path, value);
            }
        },
        EQUAL_IGNORE_CASE {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                String paramValue = "" + value;
                return builder.equal(builder.lower(path.as(String.class)), paramValue.toLowerCase());
            }
        },
        NOT_EQUAL {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                return builder.notEqual(path, value);
            }
        },
        CONTAINS {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                String paramValue = "" + value;
                return builder.like(builder.lower(path.as(String.class)),
                        "%" + paramValue.toLowerCase() + "%");
            }
        },
        START_WITH {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                String paramValue = "" + value;
                return builder.like(builder.lower(path.as(String.class)),
                        paramValue.toLowerCase() + "%");
            }
        },
        ENDS_WITH {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                String paramValue = "" + value;
                return builder.like(builder.lower(path.as(String.class)),
                        "%" + paramValue.toLowerCase());
            }
        },
        IS_NULL {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                return builder.isNull(path);
            }
        },
        IS_NOT_NULL {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                return builder.isNotNull(path);
            }
        },
        IS_EMPTY {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                return builder.equal(builder.trim(path.as(String.class)), "");
            }
        },
        IS_NOT_EMPTY {
            @Override
            public Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
                return builder.notEqual(builder.trim(path.as(String.class)), "");
            }
        }
        ;

        public abstract Predicate toPredicate(CriteriaBuilder builder, Path<?> path, Object value);
    }
}
