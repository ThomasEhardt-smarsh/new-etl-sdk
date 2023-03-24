package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.*;
import com.smarsh.dataengineering.model.digxml.ActionEvent.ActorParticipantIds;
import com.smarsh.dataengineering.model.digxml.ActionEvent.AssigneeParticipantIds;
import com.smarsh.dataengineering.model.digxml.ActionEvent.TextContent;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ActionEventBuilder implements AbstractBuilder<ActionEventBuilder, ActionEvent> {
    @NotNull
    private final String actionType;
    @NotNull
    private final String actionEventId;

    @Nullable
    private String actionState;
    @Nullable
    private String policy;
    @Nullable
    private TimeStamp eventTime;
    @Nullable
    private String parentActionEventId;
    @Nullable
    private String cocHashValue;
    @Nullable
    private String actionSubType;
    @Nullable
    private Subject subject;
    @Nullable
    private TextContent textContent;

    private final ActorParticipantIds actorParticipantIds = new ActorParticipantIds();
    private final AssigneeParticipantIds assigneeParticipantIds = new AssigneeParticipantIds();
    private final IndexableAttributes attributes = new IndexableAttributes();

    public ActionEventBuilder(@NotNull String actionType, @NotNull String actionEventId) {
        this.actionType = actionType;
        this.actionEventId = actionEventId;
    }

    @Override
    public ActionEvent build() {
        var actionEvent = new ActionEvent();

        actionEvent.setActionType(actionType);
        actionEvent.setActionEventId(actionEventId);

        Optional.ofNullable(actionState).ifPresent(actionEvent::setActionState);
        Optional.ofNullable(policy).ifPresent(actionEvent::setPolicy);
        Optional.ofNullable(eventTime).ifPresent(actionEvent::setEventTime);
        Optional.ofNullable(parentActionEventId).ifPresent(actionEvent::setParentActionEventId);
        Optional.ofNullable(cocHashValue).ifPresent(actionEvent::setCocHashValue);
        Optional.ofNullable(actionSubType).ifPresent(actionEvent::setActionSubType);
        Optional.ofNullable(subject).ifPresent(actionEvent::setSubject);
        Optional.ofNullable(textContent).ifPresent(actionEvent::setTextContent);

        if (!actorParticipantIds.getActorParticipantId().isEmpty()) {
            actionEvent.setActorParticipantIds(actorParticipantIds);
        }

        if (!assigneeParticipantIds.getAssigneeParticipantId().isEmpty()) {
            actionEvent.setAssigneeParticipantIds(assigneeParticipantIds);
        }

        if (!attributes.getAttribute().isEmpty()) {
            actionEvent.setAttributes(attributes);
        }

        return actionEvent;
    }

    public void setActionState(@NotNull String actionState) {
        this.actionState = actionState;
    }

    public void setPolicy(@NotNull String policy) {
        this.policy = policy;
    }

    public void setEventTime(@NotNull TimeStamp eventTime) {
        this.eventTime = eventTime;
    }

    public void setParentActionEventId(@NotNull String parentActionEventId) {
        this.parentActionEventId = parentActionEventId;
    }

    public void setCocHashValue(@NotNull String cocHashValue) {
        this.cocHashValue = cocHashValue;
    }

    public void setActionSubType(@NotNull String actionSubType) {
        this.actionSubType = actionSubType;
    }

    public void setSubject(@NotNull Subject subject) {
        this.subject = subject;
    }

    public void setTextContent(@NotNull TextContent textContent) {
        this.textContent = textContent;
    }

    public void addActorParticipantId(@NotNull String actorParticipantId) {
        actorParticipantIds.getActorParticipantId().add(actorParticipantId);
    }

    public void addAssigneeParticipantId(@NotNull String assigneeParticipantId) {
        assigneeParticipantIds.getAssigneeParticipantId().add(assigneeParticipantId);
    }

    public void addAttribute(@NotNull Attribute attribute) {
        attributes.getAttribute().add(attribute);
    }

    public static class TextContentBuilder implements AbstractBuilder<TextContentBuilder, TextContent> {
        // all values are optional
        @Nullable
        private String value;
        @Nullable
        private MimeType contentType;

        public TextContentBuilder() {

        }

        public TextContentBuilder(@NotNull String value, @NotNull MimeType contentType) {
            this.value = value;
            this.contentType = contentType;
        }

        @Override
        public TextContent build() {
            var textContentObject = new TextContent();

            Optional.ofNullable(value).ifPresent(textContentObject::setValue);
            Optional.ofNullable(contentType).ifPresent(textContentObject::setContentType);

            return textContentObject;
        }
    }
}
