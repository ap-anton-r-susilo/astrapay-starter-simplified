package com.astrapay.starter.service.string;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.stream.Collectors;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-09
 */
@Service
public class CodeGeneratorService {

    public static final int MAX_LENGTH = 30;
    public static final int ORIGIN_VALUE = 0;
    public static final int BOUND_VALUE = 9;
    public static final String SEPARATOR = "/";

    public String generateInvoiceNumber(String prefix) {
        prefix = Prefix.INVOICE.getPrefix() + SEPARATOR + prefix;
        return prefix + this.getRandomAlphaNumeric(prefix);
    }

    public String generateCode(String prefix) {
        return prefix + this.getRandomAlphaNumeric(prefix);
    }

    public String generateCode(String prefix, int length) {
        return prefix + this.getRandomAlphaNumeric(prefix, length);
    }

    public String generateNumericCode(String prefix) {
        return prefix + this.getRandomNumeric(prefix);
    }

    public String generateNumericCode(String prefix, int length) {
        return prefix + this.getRandomNumeric(prefix, length);
    }

    public String getRandomAlphaNumeric (String prefix) {
        return this.getRandomAlphaNumeric(prefix, MAX_LENGTH);
    }

    public String getRandomAlphaNumeric (String prefix, int length) {
        int randomLength = length - prefix.length();
        return new RandomString(randomLength).nextString();
    }

    public String getRandomNumeric(String prefix) {
        return this.getRandomNumeric(prefix, MAX_LENGTH);
    }

    public String getRandomNumeric(String prefix, int length) {
        int randomLength = length - prefix.length();
        return new SecureRandom()
                .ints(
                        randomLength,
                        ORIGIN_VALUE,
                        BOUND_VALUE
                )
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Getter
    @AllArgsConstructor
    public enum Prefix {
        INVOICE("INV");

        String prefix;
    }
}
