package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModalityTypeTest {

    @Test
    void value() {
        assertEquals("public", ModalityType.PUBLIC.value());
        assertEquals("private", ModalityType.PRIVATE.value());
        assertEquals("federated", ModalityType.FEDERATED.value());
    }

    @Test
    void fromValue() {
        assertThrows(IllegalArgumentException.class, () -> ModalityType.fromValue(null));
        assertThrows(IllegalArgumentException.class, () -> ModalityType.fromValue(""));
        assertThrows(IllegalArgumentException.class, () -> ModalityType.fromValue("hello, world"));

        // no uppercase
        assertThrows(IllegalArgumentException.class, () -> ModalityType.fromValue("PUBLIC"));

        // only lowercase
        assertEquals(ModalityType.PUBLIC, ModalityType.fromValue("public"));
        assertEquals(ModalityType.PRIVATE, ModalityType.fromValue("private"));
        assertEquals(ModalityType.FEDERATED, ModalityType.fromValue("federated"));
    }
}