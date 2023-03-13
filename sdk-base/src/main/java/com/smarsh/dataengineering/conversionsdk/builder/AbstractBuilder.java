package com.smarsh.dataengineering.conversionsdk.builder;

import java.util.function.Consumer;

public interface AbstractBuilder<C extends AbstractBuilder<C, T>, T> {
    T build();

    // this seems unnecessary but avoids 2 compilation warnings
    @SuppressWarnings("unchecked")
    private C getThis() {
        return (C) this;
    }

    default C with(Consumer<C> consumer) {
        consumer.accept(getThis());
        return getThis();
    }
}
