package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.FileObject;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.IndexableAttributes;
import com.smarsh.dataengineering.model.digxml.LexiconHit;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;

// TODO: implement
public class FileObjectWriter extends AbstractXmlWriter<FileObject> {
    protected FileObjectWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(FileObject fileObject) throws XMLStreamException {
        writeStartElement("file-object", ITM);

        // attributes
        // NONE

        // elements
        // REQUIRED
        writeElementWithPlainText("file-object-id", fileObject.getFileObjectId(), ITM);
        // OPTIONAL
        if (Objects.nonNull(fileObject.getParentFileObjectId())) {
            writeElementWithPlainText("parent-file-object-id", fileObject.getParentFileObjectId(), ITM);
        }
        // OPTIONAL
        if (Objects.nonNull(fileObject.getEventTime())) {
            new TimeStampWriter(xmlStreamWriter).writeTimeStamp("event-time", fileObject.getEventTime(), ITM);
        }
        // REQUIRED
        writeElementWithPlainText("participant-id", fileObject.getParticipantId(), ITM);
        // OPTIONAL
        if (Objects.nonNull(fileObject.getFileObjectUrl())) {
            writeElementWithPlainText(
                    "file-object-url",
                    fileObject.getFileObjectUrl(),
                    ITM
            );
        }
        // OPTIONAL
        if (Objects.nonNull(fileObject.getDuration())) {
            // TODO: what is this value?
            writeElementWithPlainText("duration", String.valueOf(fileObject.getDuration()), ITM);
        }
        // OPTIONAL
        if (Objects.nonNull(fileObject.getFileContent())) {
            new FileContentWriter(xmlStreamWriter).write(fileObject.getFileContent());
        }

        // OPTIONAL: attributes
        if (Objects.nonNull(fileObject.getAttributes()) && !fileObject.getAttributes().getAttribute().isEmpty()) {
            writeStartElement("attributes", ITM);

            var attributeWriter = new IndexableAttributeWriter(xmlStreamWriter);

            for (IndexableAttributes.Attribute attribute : fileObject.getAttributes().getAttribute()) {
                attributeWriter.write(attribute);
            }

            writeEndElement("attributes", ITM);
        }

        // OPTIONAL: lexicon hits
        if (Objects.nonNull(fileObject.getLexiconHits()) && !fileObject.getLexiconHits().getLexiconHit().isEmpty()) {
            writeStartElement("lexicon-hits", ITM);

            var lexiconHitWriter = new LexiconHitWriter(xmlStreamWriter);
            for (LexiconHit lexiconHit : fileObject.getLexiconHits().getLexiconHit()) {
                lexiconHitWriter.write(lexiconHit);
            }

            writeEndElement("lexicon-hits", ITM);
        }



        writeEndElement("file-object", ITM);
    }
}
