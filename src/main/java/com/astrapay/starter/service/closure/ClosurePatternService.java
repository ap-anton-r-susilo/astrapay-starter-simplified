package com.astrapay.starter.service.closure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @param <T> Entity
 * @param <V> Closure
 * @param <ID> Composite Key Class
 * @param <K> Data Type of Closure Primary Key
 *
 * public interface DepositApprovalClosureRepository extends JpaClosureRepository<DepositApprovalClosure, Long> {
 * return new ClosurePatternService<DepositApproval, DepositApprovalClosure, Long>(depositApprovalClosureRepository);
 *
 * @author raymond on 22/05/23
 */
@RequiredArgsConstructor
public class ClosurePatternService<T extends Node<K, V>, V extends Closure<T>, ID, K> {

  private final JpaClosureRepository<V, ID, K> jpaClosureRepository;
  private final JpaRepository<T, K> entityRepository;

  private static final Integer INITIAL_DEPTH = 0;
  private static final Integer RANGE_DEPTH = 1;

  /**
   * @apiNote this API will generate closures and save it
   * @param entity or node of closure
   * @param class of Closure
   * @return List of created Closures
   */
  public List<V> saveAndGenerateClosure(T entity, Class<V> clazz) {
    List<V> closureList = this.generateClosure(entity, clazz);
    jpaClosureRepository.saveAll(closureList);
    return closureList;
  }

  /**
   * @apiNote this API will :
   * 1. Generate closure(s)
   * 2. Assign the generated closure(s) to entity
   * 3. Save the entity (alongside the closure(s) IF cascade type = ALL / PERSIST / MERGE)
   *
   * @param entity or node of closure
   * @param class of Closure
   * @return List of created Closures
   */
  public List<V> saveEntityAndGenerateClosure(T entity, Class<V> clazz) {
    List<V> closureList = this.generateClosure(entity, clazz);

    entity.setAncestors(closureList);

    entityRepository.save(entity);
    return closureList;
  }

  public Page<V> findAll(Specification<V> specification, Pageable pageable) {
    return jpaClosureRepository.findAll(specification, pageable);
  }

  public List<V> findByDescendantId(K descendantId) {
    return jpaClosureRepository.findByDescendantId(descendantId);
  }

  public List<V> findByAncestorId(K ancestorId) {
    return jpaClosureRepository.findByAncestorId(ancestorId);
  }

  public List<V> findByDepth(int depth) {
    return jpaClosureRepository.findByDepth(depth);
  }

  public List<V> findByAncestorIdAndDescendantId(K ancestorId, K descendantId) {
    return jpaClosureRepository.findByAncestorIdAndDescendantId(ancestorId, descendantId);
  }

  public List<V> findByAncestorIdAndDepth(K ancestorId, int depth) {
    return jpaClosureRepository.findByAncestorIdAndDepth(ancestorId, depth);
  }

  public List<V> findByDescendantIdAndDepth(K descendantId, int depth) {
    return jpaClosureRepository.findByDescendantIdAndDepth(descendantId, depth);
  }

  public List<V> updateClosure(T entity, Class<V> clazz) {
    this.deleteClosure(entity);

    return this.saveAndGenerateClosure(entity, clazz);
  }

  public void deleteClosure(T entity) {
    List<V> existingClosures= jpaClosureRepository.findByDescendantId(entity.getId());

    if(!existingClosures.isEmpty()) {
      jpaClosureRepository.deleteAll(existingClosures);
    }
  }

  private List<V> generateClosure(T entity, Class<V> clazz) {
    List<V> closureList = new ArrayList<>();

    V selfClosure = getNewInstance(clazz);
    selfClosure.setAncestor(entity);
    selfClosure.setDescendant(entity);

    selfClosure.setDepth(INITIAL_DEPTH);

    closureList.add(selfClosure);

    if (Objects.nonNull(entity.getParentId())) {
      List<V> existingClosureList = jpaClosureRepository.findByDescendantId(entity.getParentId());

      existingClosureList.forEach(closure -> {
        V newClosure = getNewInstance(clazz);
        newClosure.setAncestor(closure.getAncestor());
        newClosure.setDescendant(entity);
        newClosure.setDepth(closure.getDepth() + RANGE_DEPTH);
        closureList.add(newClosure);
      });
    }

    return closureList;
  }

  private V getNewInstance(Class<V> clazz) {
    try {
      return clazz.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
             NoSuchMethodException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
