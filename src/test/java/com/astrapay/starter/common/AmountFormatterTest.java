package com.astrapay.starter.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class AmountFormatterTest {
    
    @InjectMocks
    AmountFormatter amountFormatter;
    private static final String DEBIT_PREFIX = "-";

    @Test
    public void testGetFormattedIdrString() {
        BigDecimal amount = BigDecimal.valueOf(5000);
        assertEquals(amountFormatter.getIdrString(amount), "Rp5.000");
    }
    
    @Test
    public void testGetPrefixFormattedIdrString() {
        BigDecimal amount = BigDecimal.valueOf(5000);
        assertEquals(amountFormatter.getIdrString(DEBIT_PREFIX, amount), "-Rp5.000");
    }
}
