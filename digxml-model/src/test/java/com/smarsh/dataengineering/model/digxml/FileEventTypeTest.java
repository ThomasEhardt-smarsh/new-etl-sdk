package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileEventTypeTest {

    @Test
    void value() {
        assertEquals("body", FileEventType.BODY.value());
        assertEquals("raw", FileEventType.RAW.value());
        assertEquals("inline", FileEventType.INLINE.value());
        assertEquals("attachment", FileEventType.ATTACHMENT.value());
    }

    @Test
    void fromValue() {
        assertThrows(IllegalArgumentException.class, () -> FileEventType.fromValue(null));
        assertThrows(IllegalArgumentException.class, () -> FileEventType.fromValue(""));
        assertThrows(IllegalArgumentException.class, () -> FileEventType.fromValue("hello, world"));

        // no uppercase
        assertThrows(IllegalArgumentException.class, () -> FileEventType.fromValue("BODY"));

        // only lowercase
        assertEquals(FileEventType.BODY, FileEventType.fromValue("body"));
        assertEquals(FileEventType.RAW, FileEventType.fromValue("raw"));
        assertEquals(FileEventType.INLINE, FileEventType.fromValue("inline"));
        assertEquals(FileEventType.ATTACHMENT, FileEventType.fromValue("attachment"));

    }
}