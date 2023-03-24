package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncodingTest {

    @Test
    void value() {
        assertEquals("base64", Encoding.BASE_64.value());
        assertEquals("text", Encoding.TEXT.value());
    }

    @Test
    void fromValue() {
        assertThrows(IllegalArgumentException.class, () -> Encoding.fromValue(null));
        assertThrows(IllegalArgumentException.class, () -> Encoding.fromValue(""));
        assertThrows(IllegalArgumentException.class, () -> Encoding.fromValue("hello, world"));
        // case must match exactly
        assertThrows(IllegalArgumentException.class, () -> Encoding.fromValue("BASE64"));

        // only lowercase is allowed
        assertEquals(Encoding.BASE_64, Encoding.fromValue("base64"));
        assertEquals(Encoding.TEXT, Encoding.fromValue("text"));
    }
}