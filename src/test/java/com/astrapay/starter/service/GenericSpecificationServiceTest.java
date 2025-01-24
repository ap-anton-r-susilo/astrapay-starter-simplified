package com.astrapay.starter.service;

import com.astrapay.starter.service.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.criteria.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GenericSpecificationServiceTest {

    @Mock
    private CriteriaBuilder criteriaBuilderMock;

    private CriteriaQuery criteriaQueryMock;

    private Root<EntityTest> entityTestRoot;

    @BeforeEach
    public void setUp() {
        criteriaQueryMock = mock(CriteriaQuery.class);
        entityTestRoot = mock(Root.class);
    }

    @Test
    void addTest_SUCCESS() {
        GenericSpecificationService<EntityTest> genericSpecificationService = new GenericSpecificationService<>();
        genericSpecificationService.add(new SearchCriteria<>("createdAt", "2021-01-01", SearchCriteria.SearchOperation.GREATER_THAN_EQUAL));
        Assertions.assertNotNull(genericSpecificationService);
    }

    @Test
    void toPredicateTest_SUCCESS() {
        Path namePathMock = mock(Path.class);
        Mockito.when(entityTestRoot.get("name")).thenReturn(namePathMock);

        Expression nameExpressionMock = mock(Expression.class);
        Predicate nameEqualsPredicateMock = mock(Predicate.class);
        Mockito.when(criteriaBuilderMock.equal(nameExpressionMock, "Budi")).thenReturn(nameEqualsPredicateMock);

        SearchCriteria<String> searchCriteria = new SearchCriteria<>("name", "Budi", SearchCriteria.SearchOperation.EQUAL);
        GenericSpecificationService<EntityTest> genericSpecificationService = new GenericSpecificationService<>();
        genericSpecificationService.add(searchCriteria);
        Predicate predicate = genericSpecificationService.toPredicate(entityTestRoot, criteriaQueryMock, criteriaBuilderMock);

        Mockito.verify(entityTestRoot, times(1)).get("name");
        verifyNoMoreInteractions(entityTestRoot);

        // TODO: predicate always null
        Assertions.assertNull(predicate);
    }

    @Test
    void toPredicateTest_withSpecification_SUCCESS() {
        Path namePathMock = mock(Path.class);
        Mockito.when(entityTestRoot.get("name")).thenReturn(namePathMock);

        Expression nameExpressionMock = mock(Expression.class);
        Predicate nameEqualsPredicateMock = mock(Predicate.class);
        Mockito.when(criteriaBuilderMock.equal(nameExpressionMock, "Budi")).thenReturn(nameEqualsPredicateMock);

        GenericSpecificationService<EntityTest> genericSpecificationService = new GenericSpecificationService<>(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), "abc")
        );
        genericSpecificationService.add(new SearchCriteria<>("name", "Budi", SearchCriteria.SearchOperation.EQUAL));
        genericSpecificationService.add(new SearchCriteria<>("name", "Budi", SearchCriteria.SearchOperation.EQUAL_IGNORE_CASE));
        Predicate predicate = genericSpecificationService.toPredicate(entityTestRoot, criteriaQueryMock, criteriaBuilderMock);

        Mockito.verify(entityTestRoot, times(3)).get("name");
        verifyNoMoreInteractions(entityTestRoot);

        // TODO: predicate always null
        Assertions.assertNull(predicate);
    }

    @Test
    void toPredicateTestGreaterThanEqual_SUCCESS() {
        Path namePathMock = mock(Path.class);
        Mockito.when(entityTestRoot.get("createdDate")).thenReturn(namePathMock);
        Mockito.when(entityTestRoot.get("basicPrice")).thenReturn(namePathMock);
        Mockito.when(entityTestRoot.get("name")).thenReturn(namePathMock);

        Expression nameExpressionMock = mock(Expression.class);
        Predicate nameEqualsPredicateMock = mock(Predicate.class);
        Mockito.when(criteriaBuilderMock.greaterThan(nameExpressionMock, LocalDateTime.parse("2024-05-08T00:00:00"))).thenReturn(nameEqualsPredicateMock);
        Mockito.when(criteriaBuilderMock.notEqual(nameExpressionMock, BigDecimal.valueOf(1000))).thenReturn(nameEqualsPredicateMock);
        Mockito.when(criteriaBuilderMock.like(nameExpressionMock, "sun")).thenReturn(nameEqualsPredicateMock);

        GenericSpecificationService<EntityTest> genericSpecificationService = new GenericSpecificationService<>();
        genericSpecificationService.add(new SearchCriteria<>("createdDate", LocalDateTime.parse("2024-05-08T00:00:00"), SearchCriteria.SearchOperation.GREATER_THAN_EQUAL));
        genericSpecificationService.add(new SearchCriteria<>("createdDate", LocalDateTime.parse("2024-05-08T00:00:00"), SearchCriteria.SearchOperation.LESS_THAN_EQUAL));
        genericSpecificationService.add(new SearchCriteria<>("createdDate", LocalDateTime.parse("2024-05-08T00:00:00"), SearchCriteria.SearchOperation.LESS_THAN));
        genericSpecificationService.add(new SearchCriteria<>("createdDate", LocalDateTime.parse("2024-05-08T00:00:00"), SearchCriteria.SearchOperation.GREATER_THAN));
        genericSpecificationService.add(new SearchCriteria<>("basicPrice", BigDecimal.valueOf(1000), SearchCriteria.SearchOperation.NOT_EQUAL));
        genericSpecificationService.add(new SearchCriteria<>("name", "sun", SearchCriteria.SearchOperation.MATCH));
        genericSpecificationService.add(new SearchCriteria<>("name", "sun", SearchCriteria.SearchOperation.MATCH_END));

        Predicate predicate = genericSpecificationService.toPredicate(entityTestRoot, criteriaQueryMock, criteriaBuilderMock);

        Mockito.verify(entityTestRoot, times(4)).get("createdDate");
        Mockito.verify(entityTestRoot, times(1)).get("basicPrice");
        Mockito.verify(entityTestRoot, times(2)).get("name");
        verifyNoMoreInteractions(entityTestRoot);

        // TODO: predicate always null
        Assertions.assertNull(predicate);
    }

    class EntityTest {
        private String name;
    }
}
