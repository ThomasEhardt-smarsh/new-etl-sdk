package com.smarsh.dataengineering.conversionsdk.namespace;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Namespace(@NotNull String localName, @NotNull String namespaceURI) {

    public Namespace {
        if (localName.isBlank()) {
            throw new IllegalArgumentException("localName cannot be empty/blank");
        }

        if (namespaceURI.isBlank()) {
            throw new IllegalArgumentException("namespaceURI cannot be empty/blank");
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
