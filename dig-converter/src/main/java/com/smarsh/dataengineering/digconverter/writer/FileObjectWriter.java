package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.FileObject;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;

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
            // TODO: need an example of this
            new TimeStampWriter(xmlStreamWriter).write(fileObject.getEventTime());
        }
        // REQUIRED
        writeElementWithPlainText("participant-id", fileObject.getParticipantId(), ITM);
        // OPTIONAL
        if (Objects.nonNull(fileObject.getFileObjectUrl())) {
            writeElementWithPlainText(
                    "file-object-url",
                    URLEncoder.encode(fileObject.getFileObjectUrl(), StandardCharsets.UTF_8),
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


        writeEndElement("file-object", ITM);
    }
}
