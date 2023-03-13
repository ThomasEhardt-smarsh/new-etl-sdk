package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.IndexableAttributes;
import com.smarsh.dataengineering.model.TextObject;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;

public class TextObjectWriter extends AbstractXmlWriter<TextObject> {
    protected TextObjectWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(TextObject textObject) throws XMLStreamException {
        writeStartElement("text-object", ITM);

        // attributes
        // NONE

        // elements
        // REQUIRED
        writeElementWithPlainText("text-object-id", textObject.getTextObjectId(), ITM);
        // OPTIONAL
        if (Objects.nonNull(textObject.getParentTextObjectId())) {
            writeElementWithPlainText("parent-text-object-id", textObject.getParentTextObjectId(), ITM);
        }

        // OPTIONAL but always has a value
        writeElementWithPlainText("system-flag", String.valueOf(textObject.isSystemFlag()), ITM);

        // REQUIRED
        writeElementWithPlainText("text-object-style", textObject.getTextObjectStyle().value(), ITM);

        // OPTIONAL
        if (Objects.nonNull(textObject.getTextObjectType())) {
            writeElementWithPlainText("text-object-type", textObject.getTextObjectType(), ITM);
        }

        // OPTIONAL
        if (Objects.nonNull(textObject.getTextObjectSubType())) {
            writeElementWithPlainText("text-object-sub-type", textObject.getTextObjectSubType(), ITM);
        }

        // REQUIRED
        new TimeStampWriter(xmlStreamWriter).writeTimeStamp("event-time", textObject.getEventTime(), ITM);

        // REQUIRED
        writeElementWithPlainText("participant-id", textObject.getParticipantId(), ITM);

        // OPTIONAL
        if (Objects.nonNull(textObject.getTextObjectUrl())) {
            writeElementWithPlainText(
                    "text-object-url",
                    URLEncoder.encode(textObject.getTextObjectUrl(), StandardCharsets.UTF_8),
                    ITM
            );
        }

        // OPTIONAL
        if (Objects.nonNull(textObject.getFileEventIds()) && !textObject.getFileEventIds().getFileEventId().isEmpty()) {
            writeStartElement("file-event-ids", ITM);

            for (String fileEventId : textObject.getFileEventIds().getFileEventId()) {
                writeElementWithPlainText("file-event-id", fileEventId, ITM);
            }

            writeEndElement("file-event-ids", ITM);
        }

        // OPTIONAL
        if (Objects.nonNull(textObject.getLinkCommunicationObjectId())) {
            writeElementWithPlainText("link-communication-object-id", textObject.getLinkCommunicationObjectId(), ITM);
        }
        // OPTIONAL (should always have a value)
        if (Objects.nonNull(textObject.getTextContent())) {
            new TextContentWriter(xmlStreamWriter).write(textObject.getTextContent());
        }
        // OPTIONAL
        if (Objects.nonNull(textObject.getAttributes()) && !textObject.getAttributes().getAttribute().isEmpty()) {
            writeStartElement("attributes", ITM);

            var indexableAttributeWriter = new IndexableAttributeWriter(xmlStreamWriter);
            for (IndexableAttributes.Attribute attribute : textObject.getAttributes().getAttribute()) {
                indexableAttributeWriter.write(attribute);
            }

            writeEndElement("attributes", ITM);
        }

        writeEndElement("text-object", ITM);
    }
}
