package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupsTest {

    @Test
    void getGroup() {
        // validate that we never get a null list
        assertNotEquals(null, new Groups().getGroup());
        // and it's null by default
        assertTrue(new Groups().getGroup().isEmpty());
    }
}