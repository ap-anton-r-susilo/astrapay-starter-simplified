package com.astrapay.starter.service.closure;

import com.astrapay.starter.service.GenericSpecificationService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.*;

/**
 * @author raymond on 13/06/23
 */
@ExtendWith(SpringExtension.class)
class ClosurePatternServiceTest {

    private ClosurePatternService closurePatternService;

    @Mock
    private TestEntityClosureRepository testEntityClosureRepository;

    @Mock
    private JpaRepository<TestEntity, UUID> testEntityRepository;

    @Mock
    private List<TestEntityClosure> testEntityClosureArrayList;


    @BeforeEach
    public void setup() {
        closurePatternService = new ClosurePatternService<TestEntity, TestEntityClosure, TestCompositeKeyId, UUID>(testEntityClosureRepository, testEntityRepository);
    }

    @Test
    void testCreateClosure_EmptyAncestor_ShouldCreated() {
        TestEntity testEntity = new TestEntity();
        List<TestEntityClosure> testEntityClosures = closurePatternService.saveAndGenerateClosure(testEntity, TestEntityClosure.class);
        Assertions.assertEquals(1, testEntityClosures.size());
    }

    @Test
    void testCreateClosure_ExistAncestor_ShouldCreated() {
        TestEntityClosure firstClosure = Mockito.mock(TestEntityClosure.class);
        Iterator<TestEntityClosure> mockIterator = Mockito.mock(Iterator.class);


        Mockito.when(testEntityClosureRepository.findByDescendantId(Mockito.any())).thenReturn(testEntityClosureArrayList);
        Mockito.doCallRealMethod().when(testEntityClosureArrayList).forEach(Mockito.any(Consumer.class));

        Mockito.when(testEntityClosureArrayList.size()).thenReturn(1);
        Mockito.when(testEntityClosureArrayList.iterator()).thenReturn(mockIterator);
        Mockito.when(mockIterator.hasNext()).thenReturn(true, false);
        Mockito.when(mockIterator.next()).thenReturn(firstClosure);

        TestEntity testEntity = Mockito.mock(TestEntity.class);
        Mockito.when(testEntity.getParentId()).thenReturn(UUID.randomUUID());

        List<TestEntityClosure> testEntityClosures = closurePatternService.saveAndGenerateClosure(testEntity, TestEntityClosure.class);
        Assertions.assertEquals(2, testEntityClosures.size());
    }

    @Test
    void testCreateEntityAndClosure_EmptyAncestor_ShouldCreated() {
        TestEntity testEntity = new TestEntity();
        List<TestEntityClosure> testEntityClosureList = closurePatternService.saveEntityAndGenerateClosure(testEntity, TestEntityClosure.class);
        Assertions.assertEquals(1, testEntityClosureList.size());
    }

    @Test
    void testFindAll_Criteria_ShouldReturn() {
        GenericSpecificationService<TestEntityClosure> genericSpecificationService = Mockito.mock(GenericSpecificationService.class);
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<TestEntityClosure> closurePage = Mockito.mock(PageImpl.class);

        Mockito.when(testEntityClosureRepository.findAll(eq(genericSpecificationService), any(Pageable.class)))
                .thenReturn(closurePage);

        Page<TestEntityClosure> entityClosurePage = closurePatternService.findAll(genericSpecificationService, pageable);
        Assertions.assertNotNull(entityClosurePage);
    }

    @Test
    void testFindByDescendantId_ShouldReturn() {
        Mockito.when(testEntityClosureRepository.findByDescendantId(Mockito.any())).thenReturn(testEntityClosureArrayList);

        List<TestEntityClosure> testEntityClosures = closurePatternService.findByDescendantId(UUID.randomUUID());
        Assertions.assertNotNull(testEntityClosures);
    }

    @Test
    void testFindByDescendantId_ShouldReturnEmptyList() {
        UUID descendantId = null;

        List<TestEntityClosure> testEntityClosureList = closurePatternService.findByDescendantId(descendantId);

        Assertions.assertEquals(0, testEntityClosureList.size());
    }

    @Test
    void testFindByAncestorId_ShouldReturn() {
        Mockito.when(testEntityClosureRepository.findByAncestorId(Mockito.any())).thenReturn(testEntityClosureArrayList);

        List<TestEntityClosure> testEntityClosures = closurePatternService.findByAncestorId(UUID.randomUUID());
        Assertions.assertNotNull(testEntityClosures);
    }

    @Test
    void testFindByDepth_ShouldReturn() {
        Mockito.when(testEntityClosureRepository.findByDepth(Mockito.anyInt())).thenReturn(testEntityClosureArrayList);

        List<TestEntityClosure> testEntityClosures = closurePatternService.findByDepth(1);
        Assertions.assertNotNull(testEntityClosures);
    }

    @Test
    void testFindByAncestorIdAndDescendantId_ShouldReturn() {
        Mockito.when(testEntityClosureRepository.findByAncestorIdAndDescendantId(Mockito.any(), Mockito.any())).thenReturn(testEntityClosureArrayList);

        List<TestEntityClosure> testEntityClosures = closurePatternService.findByAncestorIdAndDescendantId(UUID.randomUUID(), UUID.randomUUID());
        Assertions.assertNotNull(testEntityClosures);
    }

    @Test
    void testFindByAncestorIdAndDepth_ShouldReturn() {
        Mockito.when(testEntityClosureRepository.findByAncestorIdAndDepth(Mockito.any(), Mockito.anyInt())).thenReturn(testEntityClosureArrayList);

        List<TestEntityClosure> testEntityClosures = closurePatternService.findByAncestorIdAndDepth(UUID.randomUUID(), 1);
        Assertions.assertNotNull(testEntityClosures);
    }

    @Test
    void testFindByDescendantIdAndDepth_ShouldReturn() {
        Mockito.when(testEntityClosureRepository.findByDescendantIdAndDepth(Mockito.any(), Mockito.anyInt())).thenReturn(testEntityClosureArrayList);

        List<TestEntityClosure> testEntityClosures = closurePatternService.findByDescendantIdAndDepth(UUID.randomUUID(), 1);
        Assertions.assertNotNull(testEntityClosures);
    }

    @Test
    void testUpdateClosure_ExistClosure_ShouldRegenerated() {

        TestEntityClosure firstClosure = Mockito.mock(TestEntityClosure.class);
        Iterator<TestEntityClosure> mockIterator = Mockito.mock(Iterator.class);


        Mockito.when(testEntityClosureRepository.findByDescendantId(Mockito.any())).thenReturn(testEntityClosureArrayList);
        Mockito.doNothing().when(testEntityClosureRepository).deleteAll(Mockito.any(List.class));

        Mockito.doCallRealMethod().when(testEntityClosureArrayList).forEach(Mockito.any(Consumer.class));

        Mockito.when(testEntityClosureArrayList.size()).thenReturn(1);
        Mockito.when(testEntityClosureArrayList.iterator()).thenReturn(mockIterator);
        Mockito.when(mockIterator.hasNext()).thenReturn(true, false);
        Mockito.when(mockIterator.next()).thenReturn(firstClosure);

        TestEntity testEntity = Mockito.mock(TestEntity.class);
        Mockito.when(testEntity.getParentId()).thenReturn(UUID.randomUUID());

        List<TestEntityClosure> testEntityClosures = closurePatternService.updateClosure(testEntity, TestEntityClosure.class);
        Assertions.assertEquals(2, testEntityClosures.size());
    }

}

class TestEntity implements Node<UUID, TestEntityClosure> {

    @Override
    public void setCreatedAt(LocalDateTime localDateTime) {
    }

    @Override
    public void setUpdatedAt(LocalDateTime localDateTime) {
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public UUID getParentId() {
        return null;
    }

    @Override
    public void setAncestors(List<TestEntityClosure> ancestors) {
    }

    @Override
    public void setDescendants(List<TestEntityClosure> descendants) {

    }
}

@Data
@NoArgsConstructor
class TestEntityClosure implements Closure<TestEntity> {
    @Override
    public void setDepth(Integer descendant) {

    }

    @Override
    public void setCreatedAt(LocalDateTime localDateTime) {

    }

    @Override
    public void setUpdatedAt(LocalDateTime localDateTime) {

    }

    @Override
    public Integer getDepth() {
        return 1;
    }

    @Override
    public TestEntity getAncestor() {
        return null;
    }

    @Override
    public void setAncestor(TestEntity ancestor) {

    }

    @Override
    public TestEntity getDescendant() {
        return null;
    }

    @Override
    public void setDescendant(TestEntity descendant) {

    }
}

class TestCompositeKeyId implements Serializable {
    UUID ancestorId;
    UUID descendantId;
}

interface TestEntityClosureRepository extends JpaClosureRepository<TestEntityClosure, TestCompositeKeyId, UUID> {}
