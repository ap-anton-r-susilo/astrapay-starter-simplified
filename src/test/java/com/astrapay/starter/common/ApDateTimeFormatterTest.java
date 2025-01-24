package com.astrapay.starter.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ApDateTimeFormatterTest {
    
    @InjectMocks
    ApDateTimeFormatter apDateTimeFormatter;

    @Test
    public void testGetMonthNameDateTimeMinuteString() {
        LocalDateTime dateTime = LocalDateTime.of(2022, Month.OCTOBER,1,12,02);
        assertEquals(apDateTimeFormatter.getMonthNameDateTimeString(dateTime), "01 Okt 2022, 12:02");
    }
}
