package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.*;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class InteractionBuilder implements AbstractBuilder<InteractionBuilder, Interaction> {
    @NotNull
    private final TimeFrame timeFrame;

    @NotNull
    private final String communicationObjectId;

    @Nullable
    private String parentCommunicationObjectId;

    @Nullable
    private String threadObjectId;

    @Nullable
    private String resourceLocation;

    @NotNull
    private final Modality modality;

    @Nullable
    private Subject subject;

    private final IndexableAttributes attributes = new IndexableAttributes();
    private final Interaction.Participants participants = new Interaction.Participants();

    private final Interaction.FileEvents fileEvents = new Interaction.FileEvents();
    private final Interaction.TextEvents textEvents = new Interaction.TextEvents();
    private final Interaction.ActionEvents actionEvents = new Interaction.ActionEvents();
    private final Interaction.PolicyEvents policyEvents = new Interaction.PolicyEvents();
    private final IndexableAttributes postAttributes = new IndexableAttributes();

    @Nullable
    private String elementCocMasterHashValue;

    @Nullable
    private Long nativeDataSize;

    @NotNull
    private final String interactionEventId;

    @Nullable
    private Boolean historicalDataFlag;

    @Nullable
    private String dataState;

    @Nullable
    private String cocHashAlgorithm;

    @Nullable
    private String cocHashEncoding;

    @Nullable
    private String cocHashType;

    @Nullable
    private String attributeCocMasterHashValue;

    @Nullable
    private String cocHashValue;

    @Nullable
    private String cocVersion;

    public InteractionBuilder(
            @NotNull TimeFrame timeFrame,
            @NotNull String communicationObjectId,
            @NotNull Modality modality,
            @NotNull String interactionEventId) {
        this.timeFrame = timeFrame;
        this.communicationObjectId = communicationObjectId;
        this.modality = modality;
        this.interactionEventId = interactionEventId;
    }

    public Interaction build() {
        var interaction = new Interaction();

        interaction.setTimeFrame(timeFrame);
        interaction.setCommunicationObjectId(communicationObjectId);

        Optional.ofNullable(parentCommunicationObjectId).ifPresent(interaction::setParentCommunicationObjectId);
        Optional.ofNullable(threadObjectId).ifPresent(interaction::setThreadObjectId);
        Optional.ofNullable(resourceLocation).ifPresent(interaction::setResourceLocation);

        interaction.setModality(modality);

        Optional.ofNullable(subject).ifPresent(interaction::setSubject);

        if (!attributes.getAttribute().isEmpty()) {
            interaction.setAttributes(attributes);
        }

        if (!participants.getParticipant().isEmpty()) {
            interaction.setParticipants(participants);
        }

        if (!fileEvents.getFileEvent().isEmpty()) {
            interaction.setFileEvents(fileEvents);
        }

        if (!textEvents.getTextEvent().isEmpty()) {
            interaction.setTextEvents(textEvents);
        }

        if (!actionEvents.getActionEvent().isEmpty()) {
            interaction.setActionEvents(actionEvents);
        }

        if (!policyEvents.getPolicyEvent().isEmpty()) {
            interaction.setPolicyEvents(policyEvents);
        }

        if (!postAttributes.getAttribute().isEmpty()) {
            interaction.setPostattributes(postAttributes);
        }

        Optional.ofNullable(elementCocMasterHashValue).ifPresent(interaction::setElementCocMasterHashValue);
        Optional.ofNullable(nativeDataSize).ifPresent(interaction::setNativeDataSize);

        interaction.setInteractionEventId(interactionEventId);

        Optional.ofNullable(historicalDataFlag).ifPresent(interaction::setHistoricalDataFlag);
        Optional.ofNullable(dataState).ifPresent(interaction::setDataState);
        Optional.ofNullable(cocHashAlgorithm).ifPresent(interaction::setCocHashAlgorithm);
        Optional.ofNullable(cocHashEncoding).ifPresent(interaction::setCocHashEncoding);
        Optional.ofNullable(cocHashType).ifPresent(interaction::setCocHashType);
        Optional.ofNullable(attributeCocMasterHashValue).ifPresent(interaction::setAttributeCocMasterHashValue);
        Optional.ofNullable(cocHashValue).ifPresent(interaction::setCocHashValue);
        Optional.ofNullable(cocVersion).ifPresent(interaction::setCocVersion);

        return interaction;
    }

    public void setParentCommunicationObjectId(@NotNull String parentCommunicationObjectId) {
        this.parentCommunicationObjectId = parentCommunicationObjectId;
    }

    public void setThreadObjectId(@NotNull String threadObjectId) {
        this.threadObjectId = threadObjectId;
    }

    public void setResourceLocation(@NotNull String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public void setSubject(@NotNull Subject subject) {
        this.subject = subject;
    }

    public void setSubject(@NotNull String value, @NotNull MimeType contentType) {
        this.subject = new Subject();
        subject.setValue(value);
        subject.setContentType(contentType);
    }

    public void addAttribute(@NotNull IndexableAttributes.Attribute attribute) {
        this.attributes.getAttribute().add(attribute);
    }

    public void addParticipant(@NotNull Interaction.Participants.Participant participant) {
        this.participants.getParticipant().add(participant);
    }

    public void addFileEvent(@NotNull FileEvent fileEvent) {
        this.fileEvents.getFileEvent().add(fileEvent);
    }

    public void addTextEvent(@NotNull TextEvent textEvent) {
        this.textEvents.getTextEvent().add(textEvent);
    }

    public void addActionEvent(@NotNull ActionEvent actionEvent) {
        this.actionEvents.getActionEvent().add(actionEvent);
    }

    public void addPolicyEvent(@NotNull PolicyEvent policyEvent) {
        this.policyEvents.getPolicyEvent().add(policyEvent);
    }

    public void addPostAttribute(@NotNull IndexableAttributes.Attribute postAttribute) {
        this.postAttributes.getAttribute().add(postAttribute);
    }

    public void setElementCocMasterHashValue(@NotNull String elementCocMasterHashValue) {
        this.elementCocMasterHashValue = elementCocMasterHashValue;
    }

    public void setNativeDataSize(@NotNull Long nativeDataSize) {
        this.nativeDataSize = nativeDataSize;
    }

    public void setHistoricalDataFlag(@NotNull Boolean historicalDataFlag) {
        this.historicalDataFlag = historicalDataFlag;
    }

    public void setDataState(@NotNull String dataState) {
        this.dataState = dataState;
    }

    public void setCocHashAlgorithm(@NotNull String cocHashAlgorithm) {
        this.cocHashAlgorithm = cocHashAlgorithm;
    }

    public void setCocHashEncoding(@NotNull String cocHashEncoding) {
        this.cocHashEncoding = cocHashEncoding;
    }

    public void setCocHashType(@NotNull String cocHashType) {
        this.cocHashType = cocHashType;
    }

    public void setAttributeCocMasterHashValue(@NotNull String attributeCocMasterHashValue) {
        this.attributeCocMasterHashValue = attributeCocMasterHashValue;
    }

    public void setCocHashValue(@NotNull String cocHashValue) {
        this.cocHashValue = cocHashValue;
    }

    public void setCocVersion(@NotNull String cocVersion) {
        this.cocVersion = cocVersion;
    }
}
