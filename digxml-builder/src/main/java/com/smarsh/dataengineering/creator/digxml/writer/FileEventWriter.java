package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.FileEvent;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;

public class FileEventWriter extends AbstractXmlWriter<FileEvent> {
    protected FileEventWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(FileEvent fileEvent) throws XMLStreamException {
        writeStartElement("file-event", ITM);

        // attributes
        // REQUIRED
        writeAttribute("file-event-id", fileEvent.getFileEventId());
        // REQUIRED
        writeAttribute("file-event-type", fileEvent.getFileEventType().value());
        // OPTIONAL
        if (Objects.nonNull(fileEvent.getAction())) {
            writeAttribute("action", fileEvent.getAction().value());
        }
        // OPTIONAL but always has a value
        writeAttribute("history-flag", String.valueOf(fileEvent.isHistoryFlag()));
        // OPTIONAL
        if (Objects.nonNull(fileEvent.getCocHashValue())) {
            writeAttribute("coc-hash-value", fileEvent.getCocHashValue());
        }

        // elements
        // REQUIRED
        new FileObjectWriter(xmlStreamWriter).write(fileEvent.getFileObject());

        writeEndElement("file-event", ITM);
    }
}
