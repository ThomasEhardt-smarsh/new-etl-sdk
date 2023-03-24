package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.*;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes.Attribute;
import com.smarsh.dataengineering.model.digxml.TextObject.FileEventIds;
import com.smarsh.dataengineering.model.digxml.TextObject.LexiconHits;
import com.smarsh.dataengineering.model.digxml.TextObject.TextContent;
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
    private final FileEventIds fileEventIds = new FileEventIds();
    @Nullable
    private String linkCommunicationObjectId;
    @Nullable
    private TextContent textContent;

    private final IndexableAttributes indexableAttributes = new IndexableAttributes();
    private LexiconHits lexiconHits = new LexiconHits();

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

    public void setTextContent(@NotNull final TextContent textContent) {
        this.textContent = textContent;
    }

    public void addFileEventId(@NotNull final String fileEventId) {
        this.fileEventIds.getFileEventId().add(fileEventId);
    }

    public void addAttribute(@NotNull Attribute attribute) {
        this.indexableAttributes.getAttribute().add(attribute);
    }

    public void addLexiconHit(@NotNull LexiconHit lexiconHit) {
        this.lexiconHits.getLexiconHit().add(lexiconHit);
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

    public void setLexiconHits(@NotNull LexiconHits lexiconHits) {
        this.lexiconHits = lexiconHits;
    }
}
