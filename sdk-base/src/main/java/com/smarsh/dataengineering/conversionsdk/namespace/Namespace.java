package com.smarsh.dataengineering.conversionsdk.namespace;

import java.util.Objects;

public record Namespace(String localName, String namespaceURI) {

    public Namespace {
        if (Objects.isNull(localName) || localName.isBlank()) {
            throw new IllegalArgumentException("localName cannot be empty or null");
        }

        if (Objects.isNull(namespaceURI) || namespaceURI.isBlank()) {
            throw new IllegalArgumentException("namespaceURI cannot be empty or null");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (Objects.isNull(o) || this.getClass() != o.getClass()) {
            return false;
        }

        var oo = (Namespace) o;
        return Objects.equals(this.localName, oo.localName) && Objects.equals(this.namespaceURI, oo.namespaceURI);
    }

}
