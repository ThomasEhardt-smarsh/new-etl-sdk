package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.ActionEvent;
import com.smarsh.dataengineering.model.digxml.ActionEvent.TextContent;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes.Attribute;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.util.Objects;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;

public class ActionEventWriter extends AbstractXmlWriter<ActionEvent> {
    protected ActionEventWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(ActionEvent actionEvent) throws XMLStreamException {
        writeStartElement("action-event", ITM);

        // REQUIRED ATTRIBUTE
        writeAttribute("action-event-id", actionEvent.getActionEventId());

        // OPTIONAL ATTRIBUTE
        var parentActionEventId = actionEvent.getParentActionEventId();
        if (Objects.nonNull(parentActionEventId)) {
            writeAttribute("parent-action-event-id", parentActionEventId);
        }

        // OPTIONAL ATTRIBUTE
        var cocHashValue = actionEvent.getCocHashValue();
        if (Objects.nonNull(cocHashValue)) {
            writeAttribute("coc-hash-value", cocHashValue);
        }

        // REQUIRED
        writeElementWithPlainText("action-type", actionEvent.getActionType(), ITM);

        // OPTIONAL
        var actionSubType = actionEvent.getActionSubType();
        if (Objects.nonNull(actionSubType)) {
            writeElementWithPlainText("action-sub-type", actionSubType, ITM);
        }

        // OPTIONAL
        var actionState = actionEvent.getActionState();
        if (Objects.nonNull(actionState)) {
            writeElementWithPlainText("action-state", actionState, ITM);
        }

        // OPTIONAL
        var policy = actionEvent.getPolicy();
        if (Objects.nonNull(policy)) {
            writeElementWithPlainText("policy", policy, ITM);
        }

        // OPTIONAL
        var eventTime = actionEvent.getEventTime();
        if (Objects.nonNull(eventTime)) {
            new TimeStampWriter(xmlStreamWriter).writeTimeStamp("event-time", eventTime, ITM);
        }

        // OPTIONAL
        var subject = actionEvent.getSubject();
        if (Objects.nonNull(subject)) {
            new SubjectWriter(xmlStreamWriter).write(subject);
        }

        // OPTIONAL
        var textContent = actionEvent.getTextContent();
        if (Objects.nonNull(textContent)) {
            new TextContentWriter(xmlStreamWriter).write(textContent);
        }

        // OPTIONAL
        var actorParticipantIds = actionEvent.getActorParticipantIds();
        if (Objects.nonNull(actorParticipantIds) && !actorParticipantIds.getActorParticipantId().isEmpty()) {
            writeStartElement("actor-participant-ids", ITM);

            for (String actorParticipantId : actorParticipantIds.getActorParticipantId()) {
                writeElementWithPlainText("actor-participant-id", actorParticipantId, ITM);
            }

            writeEndElement("actor-participant-ids", ITM);
        }

        // OPTIONAL
        var assigneeParticipantIds = actionEvent.getAssigneeParticipantIds();
        if (Objects.nonNull(assigneeParticipantIds) && !assigneeParticipantIds.getAssigneeParticipantId().isEmpty()) {
            writeStartElement("assignee-participant-ids", ITM);

            for (String assigneeParticipantId : assigneeParticipantIds.getAssigneeParticipantId()) {
                writeElementWithPlainText("assignee-participant-id", assigneeParticipantId, ITM);
            }

            writeEndElement("assignee-participant-ids", ITM);
        }

        // OPTIONAL
        var attributes = actionEvent.getAttributes();
        if (Objects.nonNull(attributes) && !attributes.getAttribute().isEmpty()) {
            writeStartElement("attributes", ITM);

            for (Attribute attribute : attributes.getAttribute()) {
                new IndexableAttributeWriter(xmlStreamWriter).write(attribute);
            }

            writeEndElement("attributes", ITM);
        }

        writeEndElement("action-event", ITM);
    }

    public static class TextContentWriter extends AbstractXmlWriter<TextContent> {

        protected TextContentWriter(XMLStreamWriter xmlStreamWriter) {
            super(xmlStreamWriter);
        }

        @Override
        public void write(TextContent textContent) throws XMLStreamException {
            // all values are optional
            var value = textContent.getValue();
            var contentType = textContent.getContentType();

            var haveAnythingToWrite = Stream.of(value, contentType).anyMatch(Objects::nonNull);

            if (haveAnythingToWrite) {
                writeStartElement("text-content", ITM);

                if (Objects.nonNull(contentType)) {
                    writeAttribute("content-type", contentType.value());
                }

                if (Objects.nonNull(value)) {
                    writeCharacters(value, false);
                }

                writeEndElement("text-content", ITM);
            } else {
                // all values are optional - don't write empty element
            }
        }
    }
}
