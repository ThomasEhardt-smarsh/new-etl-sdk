package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.*;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;

public class InteractionWriter extends AbstractXmlWriter<Interaction> {
    protected InteractionWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(Interaction interaction) throws XMLStreamException {
        writeStartElement("interaction", ITM);

        // write attributes
        // REQUIRED
        writeAttribute("interaction-event-id", interaction.getInteractionEventId());
        // OPTIONAL but will always have a value
        writeAttribute("historical-data-flag", String.valueOf(interaction.isHistoricalDataFlag()));
        // OPTIONAL but will always have a value
        writeAttribute("data-state", interaction.getDataState());
        // OPTIONAL
        if (Objects.nonNull(interaction.getCocHashAlgorithm())) {
            writeAttribute("coc-hash-algorithm", interaction.getCocHashAlgorithm());
        }
        // OPTIONAL
        if (Objects.nonNull(interaction.getCocHashEncoding())) {
            writeAttribute("coc-hash-encoding", interaction.getCocHashEncoding());
        }
        // OPTIONAL
        if (Objects.nonNull(interaction.getAttributeCocMasterHashValue())) {
            writeAttribute("coc-master-hash-value", interaction.getAttributeCocMasterHashValue());
        }
        // OPTIONAL
        if (Objects.nonNull(interaction.getCocHashValue())) {
            writeAttribute("coc-hash-value", interaction.getCocHashValue());
        }
        // OPTIONAL
        if (Objects.nonNull(interaction.getCocVersion())) {
            writeAttribute("coc-version", interaction.getCocVersion());
        }

        // write the child elements
        // REQUIRED
        new TimeFrameWriter(xmlStreamWriter).write(interaction.getTimeFrame());
        // REQUIRED
        writeElementWithPlainText("communication-object-id", interaction.getCommunicationObjectId(), ITM);
        // OPTIONAL
        if (Objects.nonNull(interaction.getParentCommunicationObjectId())) {
            writeElementWithPlainText("parent-communication-object-id", interaction.getParentCommunicationObjectId(), ITM);
        }
        // OPTIONAL
        if (Objects.nonNull(interaction.getThreadObjectId())) {
            writeElementWithPlainText("thread-object-id", interaction.getThreadObjectId(), ITM);
        }
        // OPTIONAL
        if (Objects.nonNull(interaction.getResourceLocation())) {
            writeElementWithPlainText("resource-location", interaction.getResourceLocation(), ITM);
        }
        // REQUIRED
        new ModalityWriter(xmlStreamWriter).write(interaction.getModality());
        // OPTIONAL
        if (Objects.nonNull(interaction.getSubject())) {
            writeStartElement("subject", ITM);

            var contentType = interaction.getSubject().getContentType();
            writeAttribute("content-type", contentType.value());
            writeCharacters(interaction.getSubject().getValue(), interaction.getSubject().getContentType() != MimeType.TEXT_PLAIN);

            writeEndElement("subject", ITM);
        }

        // OPTIONAL: attributes
        if (Objects.nonNull(interaction.getAttributes()) && !interaction.getAttributes().getAttribute().isEmpty()) {
            writeStartElement("attributes", ITM);

            var attributeWriter = new IndexableAttributeWriter(xmlStreamWriter);

            for (IndexableAttributes.Attribute attribute : interaction.getAttributes().getAttribute()) {
                attributeWriter.write(attribute);
            }

            writeEndElement("attributes", ITM);
        }

        // OPTIONAL (though always expected): participants
        if (Objects.nonNull(interaction.getParticipants()) && !interaction.getParticipants().getParticipant().isEmpty()) {
            writeStartElement("participants", ITM);

            ParticipantWriter participantWriter = new ParticipantWriter(xmlStreamWriter);
            for (Interaction.Participants.Participant participant : interaction.getParticipants().getParticipant()) {
                participantWriter.write(participant);
            }

            writeEndElement("participants", ITM);
        }

        // OPTIONAL
        if (Objects.nonNull(interaction.getFileEvents()) && !interaction.getFileEvents().getFileEvent().isEmpty()) {
            writeStartElement("file-events", ITM);

            for (FileEvent fileEvent : interaction.getFileEvents().getFileEvent()) {
                new FileEventWriter(xmlStreamWriter).write(fileEvent);
            }

            writeEndElement("file-events", ITM);
        }

        // OPTIONAL
        if (Objects.nonNull(interaction.getTextEvents()) && !interaction.getTextEvents().getTextEvent().isEmpty()) {
            writeStartElement("text-events", ITM);

            for (TextEvent textEvent : interaction.getTextEvents().getTextEvent()) {
                new TextEventWriter(xmlStreamWriter).write(textEvent);
            }

            writeEndElement("text-events", ITM);
        }

        writeEndElement("interaction", ITM);
    }
}
