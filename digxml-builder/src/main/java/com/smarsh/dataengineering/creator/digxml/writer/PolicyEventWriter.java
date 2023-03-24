package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes.Attribute;
import com.smarsh.dataengineering.model.digxml.PolicyEvent;
import com.smarsh.dataengineering.model.digxml.PolicyEvent.TextContent;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.util.Objects;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;

public class PolicyEventWriter extends AbstractXmlWriter<PolicyEvent> {
    protected PolicyEventWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(PolicyEvent policyEvent) throws XMLStreamException {
        writeStartElement("policy-event", ITM);

        writeAttribute("policy-event-id", policyEvent.getPolicyEventId());
        writeAttribute("history-flag", String.valueOf(policyEvent.isHistoryFlag()));
        if (Objects.nonNull(policyEvent.getCocHashValue())) {
            writeAttribute("coc-hash-value", policyEvent.getCocHashValue());
        }

        writeElementWithPlainText("policy-event-type", policyEvent.getPolicyEventType(), ITM);
        if (Objects.nonNull(policyEvent.getPolicyEventSubType())) {
            writeElementWithPlainText("policy-event-sub-type", policyEvent.getPolicyEventSubType(), ITM);
        }
        writeElementWithPlainText("policy-event-category", policyEvent.getPolicyEventCategory(), ITM);

        if (Objects.nonNull(policyEvent.getEventTime())) {
            new TimeStampWriter(xmlStreamWriter).writeTimeStamp("event-time", policyEvent.getEventTime(), ITM);
        }

        // OPTIONAL
        var triggeringParticipantIds = policyEvent.getTriggeringParticipantIds();
        if (Objects.nonNull(triggeringParticipantIds) && !triggeringParticipantIds.getTriggeringParticipantId().isEmpty()) {
            writeStartElement("triggering-participant-ids", ITM);

            for (String triggeringParticipantId : triggeringParticipantIds.getTriggeringParticipantId()) {
                writeElementWithPlainText("triggering-participant-id", triggeringParticipantId, ITM);
            }

            writeEndElement("triggering-participant-ids", ITM);
        }

        // OPTIONAL
        if (Objects.nonNull(policyEvent.getPolicy())) {
            writeElementWithPlainText("policy", policyEvent.getPolicy(), ITM);
        }

        // OPTIONAL
        var actionEventIds = policyEvent.getActionEventIds();
        if (Objects.nonNull(actionEventIds) && !actionEventIds.getActionEventId().isEmpty()) {
            writeStartElement("action-event-ids", ITM);

            for (String actionEventId : actionEventIds.getActionEventId()) {
                writeElementWithPlainText("action-event-id", actionEventId, ITM);
            }

            writeEndElement("action-event-ids", ITM);
        }

        // OPTIONAL
        var triggeringTextEventIds = policyEvent.getTriggeringTextEventIds();
        if (Objects.nonNull(triggeringTextEventIds) && !triggeringTextEventIds.getTextEventId().isEmpty()) {
            writeStartElement("triggering-text-event-ids", ITM);

            for (String triggeringTextEventId : triggeringTextEventIds.getTextEventId()) {
                writeElementWithPlainText("text-event-id", triggeringTextEventId, ITM);
            }

            writeEndElement("triggering-text-event-ids", ITM);
        }

        // OPTIONAL
        var triggeringFileEventIds = policyEvent.getTriggeringFileEventIds();
        if (Objects.nonNull(triggeringFileEventIds) && !triggeringFileEventIds.getFileEventId().isEmpty()) {
            writeStartElement("triggering-file-event-ids", ITM);

            for (String triggeringFileEventId : triggeringFileEventIds.getFileEventId()) {
                writeElementWithPlainText("file-event-id", triggeringFileEventId, ITM);
            }

            writeEndElement("triggering-file-event-ids", ITM);
        }

        // OPTIONAL
        var textContent = policyEvent.getTextContent();
        if (Objects.nonNull(textContent)) {
            new TextContentWriter(xmlStreamWriter).write(textContent);
        }

        // OPTIONAL
        var attributes = policyEvent.getAttributes();
        if (Objects.nonNull(attributes) && !attributes.getAttribute().isEmpty()) {
            writeStartElement("attributes", ITM);

            for (Attribute attribute : attributes.getAttribute()) {
                new IndexableAttributeWriter(xmlStreamWriter).write(attribute);
            }

            writeEndElement("attributes", ITM);
        }

        writeEndElement("policy-event", ITM);
    }

    public static class TextContentWriter extends AbstractXmlWriter<TextContent> {

        protected TextContentWriter(XMLStreamWriter xmlStreamWriter) {
            super(xmlStreamWriter);
        }

        @Override
        public void write(TextContent textContent) throws XMLStreamException {
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
