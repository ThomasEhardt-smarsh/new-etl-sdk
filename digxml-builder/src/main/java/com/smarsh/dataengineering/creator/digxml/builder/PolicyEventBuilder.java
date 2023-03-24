package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes.Attribute;
import com.smarsh.dataengineering.model.digxml.MimeType;
import com.smarsh.dataengineering.model.digxml.PolicyEvent;
import com.smarsh.dataengineering.model.digxml.PolicyEvent.*;
import com.smarsh.dataengineering.model.digxml.TimeStamp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PolicyEventBuilder implements AbstractBuilder<PolicyEventBuilder, PolicyEvent> {
    @NotNull
    private final String policyEventType;
    @NotNull
    private final String policyEventCategory;
    @NotNull
    private final String policyEventId;

    @Nullable
    private String policyEventSubType;
    @Nullable
    private Boolean historyFlag;
    @Nullable
    private String cocHashValue;
    @Nullable
    private TimeStamp eventTime;
    @Nullable
    private String policy;
    @Nullable
    private TextContent textContent;

    private final TriggeringParticipantIds triggeringParticipantIds = new TriggeringParticipantIds();
    private final ActionEventIds actionEventIds = new ActionEventIds();
    private final TriggeringTextEventIds triggeringTextEventIds = new TriggeringTextEventIds();
    private final TriggeringFileEventIds triggeringFileEventIds = new TriggeringFileEventIds();
    private final IndexableAttributes indexableAttributes = new IndexableAttributes();

    public PolicyEventBuilder(@NotNull String policyEventType, @NotNull String policyEventCategory, @NotNull String policyEventId) {
        this.policyEventType = policyEventType;
        this.policyEventCategory = policyEventCategory;
        this.policyEventId = policyEventId;
    }

    @Override
    public PolicyEvent build() {
        var policyEvent = new PolicyEvent();

        policyEvent.setPolicyEventType(policyEventType);
        policyEvent.setPolicyEventCategory(policyEventCategory);
        policyEvent.setPolicyEventId(policyEventId);

        Optional.ofNullable(policyEventSubType).ifPresent(policyEvent::setPolicyEventSubType);
        Optional.ofNullable(historyFlag).ifPresent(policyEvent::setHistoryFlag);
        Optional.ofNullable(cocHashValue).ifPresent(policyEvent::setCocHashValue);
        Optional.ofNullable(eventTime).ifPresent(policyEvent::setEventTime);
        Optional.ofNullable(policy).ifPresent(policyEvent::setPolicy);

        if (!triggeringParticipantIds.getTriggeringParticipantId().isEmpty()) {
            policyEvent.setTriggeringParticipantIds(triggeringParticipantIds);
        }

        if (!actionEventIds.getActionEventId().isEmpty()) {
            policyEvent.setActionEventIds(actionEventIds);
        }

        if (!triggeringTextEventIds.getTextEventId().isEmpty()) {
            policyEvent.setTriggeringTextEventIds(triggeringTextEventIds);
        }

        if (!triggeringFileEventIds.getFileEventId().isEmpty()) {
            policyEvent.setTriggeringFileEventIds(triggeringFileEventIds);
        }

        Optional.ofNullable(textContent).ifPresent(policyEvent::setTextContent);

        if (!indexableAttributes.getAttribute().isEmpty()) {
            policyEvent.setAttributes(indexableAttributes);
        }

        return policyEvent;
    }

    public void setPolicyEventSubType(@NotNull String policyEventSubType) {
        this.policyEventSubType = policyEventSubType;
    }

    public void setHistoryFlag(@NotNull Boolean historyFlag) {
        this.historyFlag = historyFlag;
    }

    public void setCocHashValue(@NotNull String cocHashValue) {
        this.cocHashValue = cocHashValue;
    }

    public void setEventTime(@NotNull TimeStamp eventTime) {
        this.eventTime = eventTime;
    }

    public void addTriggeringParticipantId(@NotNull String triggeringParticipantId) {
        triggeringParticipantIds.getTriggeringParticipantId().add(triggeringParticipantId);
    }
    public void setPolicy(@NotNull String policy) {
        this.policy = policy;
    }

    public void addActionEventId(@NotNull String actionEventId) {
        actionEventIds.getActionEventId().add(actionEventId);
    }

    public void addTriggeringTextEventId(@NotNull String triggeringTextEventId) {
        triggeringTextEventIds.getTextEventId().add(triggeringTextEventId);
    }

    public void addTriggeringFileEventId(@NotNull String triggeringFileEventId) {
        triggeringFileEventIds.getFileEventId().add(triggeringFileEventId);
    }

    public void setTextContent(@NotNull TextContent textContent) {
        this.textContent = textContent;
    }

    public void addAttribute(@NotNull Attribute attribute) {
        indexableAttributes.getAttribute().add(attribute);
    }

    public static class TextContentBuilder implements AbstractBuilder<TextContentBuilder, TextContent> {
        // all values are optional
        @Nullable
        private String value;
        @Nullable
        private MimeType contentType;

        @Override
        public TextContent build() {
            var textContent = new TextContent();

            Optional.ofNullable(value).ifPresent(textContent::setValue);
            Optional.ofNullable(contentType).ifPresent(textContent::setContentType);

            return textContent;
        }

        public void setValue(@NotNull String value) {
            this.value = value;
        }

        public void setContentType(@NotNull MimeType contentType) {
            this.contentType = contentType;
        }
    }
}
