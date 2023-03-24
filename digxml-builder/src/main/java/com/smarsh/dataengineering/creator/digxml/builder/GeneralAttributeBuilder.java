package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.GeneralAttributes.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GeneralAttributeBuilder implements AbstractBuilder<GeneralAttributeBuilder, Attribute> {
    @NotNull
    private String name;

    @Nullable
    private String value;
    @Nullable
    private String contentType;

    public GeneralAttributeBuilder(@NotNull String name) {
        this.name = name;
    }

    @Override
    public Attribute build() {
        var attribute = new Attribute();

        attribute.setName(name);

        Optional.ofNullable(value).ifPresent(attribute::setValue);
        Optional.ofNullable(contentType).ifPresent(attribute::setContentType);

        return attribute;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
