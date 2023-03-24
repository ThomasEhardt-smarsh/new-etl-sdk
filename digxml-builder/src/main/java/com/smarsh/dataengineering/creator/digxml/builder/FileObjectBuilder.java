package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.model.digxml.FileObject;
import com.smarsh.dataengineering.model.digxml.FileObject.LexiconHits;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes.Attribute;
import com.smarsh.dataengineering.model.digxml.LexiconHit;
import com.smarsh.dataengineering.model.digxml.TimeStamp;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FileObjectBuilder implements AbstractBuilder<FileObjectBuilder, FileObject> {
    @NotNull
    private final String fileObjectId;

    @Nullable
    private String parentFileObjectId;

    @Nullable
    private TimeStamp eventTime;

    @Nullable
    private String participantId;

    @Nullable
    private String fileObjectUrl;

    @Nullable
    private Long duration;

    @Nullable
    private FileObject.FileContent fileContent;

    private final IndexableAttributes attributes = new IndexableAttributes();

    @Nullable
    private final LexiconHits lexiconHits = new LexiconHits();


    public FileObjectBuilder(@NotNull final String fileObjectId) {
        this.fileObjectId = fileObjectId;
    }

    public FileObject build() {
        var fileObject = new FileObject();

        fileObject.setFileObjectId(fileObjectId);

        Optional.ofNullable(parentFileObjectId).ifPresent(fileObject::setParentFileObjectId);
        Optional.ofNullable(eventTime).ifPresent(fileObject::setEventTime);
        Optional.ofNullable(participantId).ifPresent(fileObject::setParticipantId);
        Optional.ofNullable(fileObjectUrl).ifPresent(fileObject::setFileObjectUrl);
        Optional.ofNullable(duration).ifPresent(fileObject::setDuration);
        Optional.ofNullable(fileContent).ifPresent(fileObject::setFileContent);
        Optional.ofNullable(eventTime).ifPresent(fileObject::setEventTime);

        if (!attributes.getAttribute().isEmpty()) {
            fileObject.setAttributes(attributes);
        }

        if (!lexiconHits.getLexiconHit().isEmpty()) {
            fileObject.setLexiconHits(lexiconHits);
        }

        return fileObject;
    }


    public void setParentFileObjectId(@NotNull String parentFileObjectId) {
        this.parentFileObjectId = parentFileObjectId;
    }

    public void setEventTime(@NotNull TimeStamp eventTime) {
        this.eventTime = eventTime;
    }

    public void setParticipantId(@NotNull String participantId) {
        this.participantId = participantId;
    }

    public void setFileObjectUrl(@NotNull String fileObjectUrl) {
        this.fileObjectUrl = fileObjectUrl;
    }

    public void setDuration(@NotNull Long duration) {
        this.duration = duration;
    }

    public void setFileContent(@NotNull FileObject.FileContent fileContent) {
        this.fileContent = fileContent;
    }

    public void addLexiconHit(@NotNull LexiconHit lexiconHit) {
        this.lexiconHits.getLexiconHit().add(lexiconHit);
    }

    public void addAttribute(@NotNull Attribute attribute) {
        this.attributes.getAttribute().add(attribute);
    }
}
