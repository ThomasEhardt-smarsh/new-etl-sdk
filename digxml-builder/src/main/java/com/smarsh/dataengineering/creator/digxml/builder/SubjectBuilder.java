package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.MimeType;
import com.smarsh.dataengineering.model.digxml.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SubjectBuilder implements AbstractBuilder<SubjectBuilder, Subject> {
    @Nullable
    private String value;
    @Nullable
    private MimeType contentType;

    public SubjectBuilder() {

    }

    public SubjectBuilder(@NotNull String value, @NotNull MimeType contentType) {
        this.value = value;
        this.contentType = contentType;
    }

    @Override
    public Subject build() {
        var subject = new Subject();

        Optional.ofNullable(value).ifPresent(subject::setValue);
        Optional.ofNullable(contentType).ifPresent(subject::setContentType);

        return subject;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
    }

    public void setContentType(@NotNull MimeType contentType) {
        this.contentType = contentType;
    }
}
