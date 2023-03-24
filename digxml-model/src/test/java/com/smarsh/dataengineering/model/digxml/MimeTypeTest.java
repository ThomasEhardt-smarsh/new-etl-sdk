package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MimeTypeTest {

    @Test
    void value() {
        assertEquals("text/html", MimeType.TEXT_HTML.value());
        assertEquals("text/xml", MimeType.TEXT_XML.value());
        assertEquals("text/plain", MimeType.TEXT_PLAIN.value());
        assertEquals("text/rtf", MimeType.TEXT_RTF.value());
    }

    @Test
    void fromValue() {
        assertThrows(IllegalArgumentException.class, () -> MimeType.fromValue(null));
        assertThrows(IllegalArgumentException.class, () -> MimeType.fromValue(""));
        assertThrows(IllegalArgumentException.class, () -> MimeType.fromValue("hello, world"));

        // uppercase not allowed
        assertThrows(IllegalArgumentException.class, () -> MimeType.fromValue("TEXT/HTML"));

        // only lowercase
        assertEquals(MimeType.TEXT_HTML, MimeType.fromValue("text/html"));
        assertEquals(MimeType.TEXT_XML, MimeType.fromValue("text/xml"));
        assertEquals(MimeType.TEXT_PLAIN, MimeType.fromValue("text/plain"));
        assertEquals(MimeType.TEXT_RTF, MimeType.fromValue("text/rtf"));
    }
}