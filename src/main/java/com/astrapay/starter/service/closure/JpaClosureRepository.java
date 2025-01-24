package com.astrapay.starter.service.closure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Variables Definition:
 * T: any class that extends Closure class
 * ID: a composite key class
 * K: data type of the composite key (Descendant & Ancestor Id)
 **/

@NoRepositoryBean
public interface JpaClosureRepository<T, ID, K> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  List<T> findByDescendantId(K descendantId);

  List<T> findByAncestorId(K ancestorId);

  List<T> findByDepth(int depth);

  List<T> findByAncestorIdAndDescendantId(K ancestorId, K descendantId);

  List<T> findByAncestorIdAndDepth(K ancestorId, int depth);

  List<T> findByDescendantIdAndDepth(K descendantId, int depth);
}
