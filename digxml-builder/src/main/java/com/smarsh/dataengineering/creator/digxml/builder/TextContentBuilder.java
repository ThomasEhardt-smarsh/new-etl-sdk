package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.MimeType;
import com.smarsh.dataengineering.model.digxml.TextObject.TextContent;
import com.smarsh.dataengineering.model.digxml.UserVisibility;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TextContentBuilder implements AbstractBuilder<TextContentBuilder, TextContent> {
    @Nullable
    private String value;
    @Nullable
    private MimeType contentType;
    @Nullable
    private Boolean indexable;
    @Nullable
    private UserVisibility userVisibile;

    @Override
    public TextContent build() {
        var textContent = new TextContent();

        Optional.ofNullable(value).ifPresent(textContent::setValue);
        Optional.ofNullable(contentType).ifPresent(textContent::setContentType);
        Optional.ofNullable(indexable).ifPresent(textContent::setIndexable);
        Optional.ofNullable(userVisibile).ifPresent(textContent::setUserVisible);

        return textContent;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setContentType(MimeType contentType) {
        this.contentType = contentType;
    }

    public void setIndexable(Boolean indexable) {
        this.indexable = indexable;
    }

    public void setUserVisibile(UserVisibility userVisibile) {
        this.userVisibile = userVisibile;
    }
}
