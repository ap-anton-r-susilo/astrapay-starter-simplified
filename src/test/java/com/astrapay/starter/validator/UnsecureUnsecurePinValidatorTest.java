package com.astrapay.starter.validator;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.passay.PasswordValidator;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnsecureUnsecurePinValidatorTest {

    @InjectMocks
    private UnsecurePinValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private PasswordValidator passwordValidator;


    @ParameterizedTest
    @ValueSource(strings = {"123458", "987554", "246810"})
    void isValid_success(String pin) {
        //Act
        boolean actual = validator.isValid(pin, context);

        assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123456", "1234567", "abcdef", "111111", "654321", "      "})
    void isValid_failed(String pin) {

        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);

        //Act
        boolean actual = validator.isValid(pin, context);

        assertFalse(actual);
    }
}