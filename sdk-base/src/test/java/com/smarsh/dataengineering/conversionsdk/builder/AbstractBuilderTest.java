package com.smarsh.dataengineering.conversionsdk.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractBuilderTest {

    @Test
    void build() {
        var fooObject = new BuilderImpl().build();
        assertNotNull(fooObject);
        assertNull(fooObject.name());
        assertNull(fooObject.value());
    }

    @Test
    void with() {
        var fooObject = new BuilderImpl()
                .with(builder -> builder.setName("name"))
                .with(builder -> builder.setValue("value"))
                .build();

        assertEquals("name", fooObject.name());
        assertEquals("value", fooObject.value());
    }

    record Foo(String name, String value){};

    static class BuilderImpl implements AbstractBuilder<BuilderImpl, Foo> {
        private String name;
        private String value;

        @Override
        public Foo build() {
            return new Foo(name, value);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}