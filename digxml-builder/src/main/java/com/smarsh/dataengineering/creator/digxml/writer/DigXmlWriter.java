package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.Transcript;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * the base for writing digxml
 */
public class DigXmlWriter {
    private final XMLStreamWriter xmlStreamWriter;

    public DigXmlWriter(XMLStreamWriter xmlStreamWriter) {
        this.xmlStreamWriter = xmlStreamWriter;
    }

    public void writeDigXml(final Transcript transcript) throws XMLStreamException {
        xmlStreamWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");

        new TranscriptWriter(xmlStreamWriter).write(transcript);

        xmlStreamWriter.writeEndDocument();
    }
}
