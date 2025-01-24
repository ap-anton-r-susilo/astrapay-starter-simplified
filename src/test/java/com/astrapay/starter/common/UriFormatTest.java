package com.astrapay.starter.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * @author Raymond Sugiarto
 * @since 2021-09-13
 */
public class UriFormatTest {

    @Test
    public void testAddToUri() {
        UriFormat uriFormat = new UriFormat();
        uriFormat.addToUri("hallo", "cd");
        uriFormat.addToUri("name", "abc");
        uriFormat.addToUri("name", null);
        assertNotNull(uriFormat.toString());
    }
}
