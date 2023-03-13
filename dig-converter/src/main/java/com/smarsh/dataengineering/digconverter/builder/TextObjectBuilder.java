package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.*;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TextObjectBuilder implements AbstractBuilder<TextObjectBuilder, TextObject> {

    @NotNull
    private final String textObjectId;

    @Nullable
    private String parentTextObjectId;

    @Nullable
    private Boolean systemFlag;
    @NotNull
    private final TextEventType textObjectStyle;

    @Nullable
    private String textObjectType;

    @Nullable
    private String textObjectSubType;
    @NotNull
    private final TimeStamp eventTime;
    @NotNull
    private final String participantId;

    @Nullable
    private String textObjectUrl;
    private final TextObject.FileEventIds fileEventIds = new TextObject.FileEventIds();
    @Nullable
    private String linkCommunicationObjectId;
    @Nullable
    private TextObject.TextContent textContent;
    private final IndexableAttributes indexableAttributes = new IndexableAttributes();
    @Nullable
    private TextObject.LexiconHits lexiconHits;

    public TextObjectBuilder(@NotNull String textObjectId, @NotNull TextEventType textObjectStyle, @NotNull TimeStamp eventTime, @NotNull String participantId) {
        this.textObjectId = textObjectId;
        this.textObjectStyle = textObjectStyle;
        this.eventTime = eventTime;
        this.participantId = participantId;
    }

    @Override
    public TextObject build() {
        var textObject = new TextObject();

        textObject.setTextObjectId(textObjectId);
        Optional.ofNullable(parentTextObjectId).ifPresent(textObject::setParentTextObjectId);

        textObject.setSystemFlag(systemFlag);
        textObject.setTextObjectStyle(textObjectStyle);

        Optional.ofNullable(textObjectType).ifPresent(textObject::setTextObjectType);
        Optional.ofNullable(textObjectSubType).ifPresent(textObject::setTextObjectSubType);

        textObject.setEventTime(eventTime);
        textObject.setParticipantId(participantId);

        Optional.ofNullable(textObjectUrl).ifPresent(textObject::setTextObjectUrl);

        if (!fileEventIds.getFileEventId().isEmpty()) {
            textObject.setFileEventIds(fileEventIds);
        }

        Optional.ofNullable(linkCommunicationObjectId).ifPresent(textObject::setLinkCommunicationObjectId);
        Optional.ofNullable(textContent).ifPresent(textObject::setTextContent);

        if (!indexableAttributes.getAttribute().isEmpty()) {
            textObject.setAttributes(indexableAttributes);
        }

        Optional.ofNullable(lexiconHits).ifPresent(textObject::setLexiconHits);

        return textObject;
    }

    public void withTextContent(final MimeType contentType, boolean indexable, final UserVisibility userVisibility, final String value) {
        this.textContent = new TextObject.TextContent();
        this.textContent.setContentType(contentType);
        this.textContent.setIndexable(indexable);
        this.textContent.setUserVisible(userVisibility);
        this.textContent.setValue(value);
    }

    public void withAttribute(final String name, final MimeType contentType, boolean indexable, final UserVisibility userVisibility, final String value) {
        var attribute = new IndexableAttributes.Attribute();

        attribute.setName(name);
        attribute.setContentType(contentType);
        attribute.setIndexable(indexable);
        attribute.setUserVisible(userVisibility);
        attribute.setValue(value);

        this.indexableAttributes.getAttribute().add(attribute);
    }

    public void setParentTextObjectId(@NotNull String parentTextObjectId) {
        this.parentTextObjectId = parentTextObjectId;
    }

    public void setSystemFlag(@NotNull Boolean systemFlag) {
        this.systemFlag = systemFlag;
    }

    public void setTextObjectType(@NotNull String textObjectType) {
        this.textObjectType = textObjectType;
    }

    public void setTextObjectSubType(@NotNull String textObjectSubType) {
        this.textObjectSubType = textObjectSubType;
    }

    public void setTextObjectUrl(@NotNull String textObjectUrl) {
        this.textObjectUrl = textObjectUrl;
    }

    public void setLinkCommunicationObjectId(@NotNull String linkCommunicationObjectId) {
        this.linkCommunicationObjectId = linkCommunicationObjectId;
    }

    public void setLexiconHits(@NotNull TextObject.LexiconHits lexiconHits) {
        this.lexiconHits = lexiconHits;
    }
}
