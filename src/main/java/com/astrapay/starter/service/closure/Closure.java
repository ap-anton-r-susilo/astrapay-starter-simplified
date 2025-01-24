package com.astrapay.starter.service.closure;

import java.time.LocalDateTime;

/**
 * @param <T> Entity / Node
 */
public interface Closure<T> {
    void setDepth(Integer depth);
    void setCreatedAt(LocalDateTime localDateTime);
    void setUpdatedAt(LocalDateTime localDateTime);

    Integer getDepth();

    T getAncestor();
    void setAncestor(T ancestor);

    T getDescendant();
    void setDescendant(T descendant);
}
