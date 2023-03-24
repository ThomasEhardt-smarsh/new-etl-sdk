package com.smarsh.dataengineering.conversionsdk.namespace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamespaceTest {

    @Test
    void constructorTests() {
        assertThrows(IllegalArgumentException.class, () -> new Namespace("", "something"));
        assertThrows(IllegalArgumentException.class, () -> new Namespace("    ", "something"));
        assertThrows(IllegalArgumentException.class, () -> new Namespace("something", ""));
        assertThrows(IllegalArgumentException.class, () -> new Namespace("something", " "));

        // we are not doing any sort of validation check to see if the local name or namespace are valid
        assertDoesNotThrow(() -> new Namespace("hello", "world"));
    }

    @Test
    void testEquals() {
        var n1 = new Namespace("local-name", "http://example.com/hello-world");
        var n2 = new Namespace("local-name", "http://example.com/hello-world");

        // when two object have the same values, they are equal
        assertNotSame(n1, n2);
        assertEquals(n1, n2);
        assertTrue(n1.equals(n2));
        assertTrue(n2.equals(n1));

        // identity test
        assertTrue(n1.equals(n1));

        // null won't match
        assertFalse(n1.equals(null));

        // wrong object types won't match
        assertFalse(n1.equals("hello, world"));

    }

    @Test
    void localName() {
        assertEquals("local-name", new Namespace("local-name", "http://example.com/hello-world").localName());
        assertEquals("local-name", new Namespace("local-name", "namespace-uri").localName());
    }

    @Test
    void namespaceURI() {
        assertEquals("http://example.com/hello-world", new Namespace("local-name", "http://example.com/hello-world").namespaceURI());
        assertEquals("namespace-uri", new Namespace("local-name", "namespace-uri").namespaceURI());
    }
}