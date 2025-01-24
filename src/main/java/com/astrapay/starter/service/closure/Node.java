package com.astrapay.starter.service.closure;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @param <K> Data Type of Primary Key
 * @param <V> Closure
 */
public interface Node<K, V> {
    K getId();
    K getParentId();

    void setAncestors(List<V> ancestors);
    void setDescendants(List<V> descendants);

    void setCreatedAt(LocalDateTime localDateTime);
    void setUpdatedAt(LocalDateTime localDateTime);
}
