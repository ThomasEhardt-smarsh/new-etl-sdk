package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.TextEvent;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;

public class TextEventWriter extends AbstractXmlWriter<TextEvent> {
    protected TextEventWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(TextEvent textEvent) throws XMLStreamException {
        writeStartElement("text-event", ITM);

        // attributes
        // REQUIRED
        writeAttribute("text-event-id", textEvent.getTextEventId());
        // OPTIONAL but always has a value
        writeAttribute("action", textEvent.getAction().value());
        // OPTIONAL
        if (Objects.nonNull(textEvent.getOperation())) {
            writeAttribute("operation", textEvent.getOperation());
        }
        // OPTIONAL but always has a value
        writeAttribute("history-flag", String.valueOf(textEvent.isHistoryFlag()));
        // OPTIONAL
        if (Objects.nonNull(textEvent.getCocHashValue())) {
            writeAttribute("coc-hash-value", textEvent.getCocHashValue());
        }

        // elements
        // REQUIRED
        new TextObjectWriter(xmlStreamWriter).write(textEvent.getTextObject());


        writeEndElement("text-event", ITM);
    }
}
