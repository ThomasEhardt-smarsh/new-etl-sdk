package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.MimeType;
import com.smarsh.dataengineering.model.UserVisibility;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.smarsh.dataengineering.model.IndexableAttributes.Attribute;

public class IndexableAttributeBuilder implements AbstractBuilder<IndexableAttributeBuilder, Attribute> {
    @NotNull
    private final String name;

    @Nullable
    private String value;

    @Nullable
    private MimeType contentType;

    @Nullable
    private Boolean indexable;

    @Nullable
    private UserVisibility userVisibility;

    @Nullable
    private String classification;

    @Nullable
    private Boolean systemFlag;

    public IndexableAttributeBuilder(final @NotNull String name) {
        this.name = name;
    }

    @Override
    public Attribute build() {
        var attribute = new Attribute();
        attribute.setName(name);

        Optional.ofNullable(value).ifPresent(attribute::setValue);
        Optional.ofNullable(contentType).ifPresent(attribute::setContentType);
        Optional.ofNullable(indexable).ifPresent(attribute::setIndexable);
        Optional.ofNullable(userVisibility).ifPresent(attribute::setUserVisible);
        Optional.ofNullable(classification).ifPresent(attribute::setClassification);
        Optional.ofNullable(systemFlag).ifPresent(attribute::setSystemFlag);

        return attribute;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
    }

    public void setContentType(@NotNull MimeType contentType) {
        this.contentType = contentType;
    }

    public void setIndexable(@NotNull Boolean indexable) {
        this.indexable = indexable;
    }

    public void setUserVisibility(@NotNull UserVisibility userVisibility) {
        this.userVisibility = userVisibility;
    }

    public void setClassification(@NotNull String classification) {
        this.classification = classification;
    }

    public void setSystemFlag(@NotNull Boolean systemFlag) {
        this.systemFlag = systemFlag;
    }
}
