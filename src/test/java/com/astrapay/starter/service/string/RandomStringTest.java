package com.astrapay.starter.service.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-09
 */
public class RandomStringTest {

    @Test
    public void testNextString() {
        RandomString randomString = new RandomString(9);
        Assertions.assertNotNull(randomString);
        Assertions.assertNotNull(randomString.nextString());
    }
}
