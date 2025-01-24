package com.astrapay.starter.validator;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

public class UnsecurePinValidator implements ConstraintValidator<com.astrapay.starter.validator.constraint.UnsecurePin, String> {

    private static final int PIN_LENGTH = 6;
    private static final String CHAR_ALLOWED = "0123456789";

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {

        var resolver = getMessageResolver();

        var validator = new PasswordValidator(
                resolver,
                new LengthRule(PIN_LENGTH),
                new AllowedCharacterRule(CHAR_ALLOWED.toCharArray()),
                new RepeatCharacterRegexRule(PIN_LENGTH),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, PIN_LENGTH, true),
                new WhitespaceRule()
        );

        var ruleResult = validator.validate(new PasswordData(string));

        if (!ruleResult.isValid()) {
            validator.getMessages(ruleResult).stream().findFirst().ifPresent(message -> {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            });
            return false;
        }

        return true;
    }

    private static MessageResolver getMessageResolver() {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(LengthRule.ERROR_CODE_MIN, "PIN harus terdiri dari 6 karakter.");
        messageMap.put(LengthRule.ERROR_CODE_MAX, "PIN tidak boleh lebih dari 6 karakter.");
        messageMap.put(AllowedCharacterRule.ERROR_CODE, "PIN hanya boleh berisi angka.");
        messageMap.put(IllegalRegexRule.ERROR_CODE, "Biar aman, pastikan kombinasi PIN tidak berulang.");
        messageMap.put(EnglishSequenceData.Numerical.getErrorCode(), "Biar aman, pastikan kombinasi PIN tidak berurutan.");
        messageMap.put(WhitespaceRule.ERROR_CODE, "PIN tidak boleh memiliki spasi.");
        return detail -> messageMap.get(detail.getErrorCode());
    }
}