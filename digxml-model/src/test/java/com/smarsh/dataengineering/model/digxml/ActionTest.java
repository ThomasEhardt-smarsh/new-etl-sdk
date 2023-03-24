package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    @Test
    void actionFromValueTest() {
        assertThrows(IllegalArgumentException.class, () -> Action.fromValue(null));
        assertThrows(IllegalArgumentException.class, () -> Action.fromValue(""));
        assertThrows(IllegalArgumentException.class, () -> Action.fromValue("hello, world"));

        // uppercase values are not allowed
        assertThrows(IllegalArgumentException.class, () -> Action.fromValue("DELETE"));

        // all values must be lowercase
        assertEquals(Action.DELETE, Action.fromValue("delete"));
        assertEquals(Action.UPDATE, Action.fromValue("update"));
        assertEquals(Action.CREATE, Action.fromValue("create"));
    }

    @Test
    void actionGetValueTest() {
        assertEquals("delete", Action.DELETE.value());
        assertEquals("update", Action.UPDATE.value());
        assertEquals("create", Action.CREATE.value());
    }

}