package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.TimeStamp;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.conversionsdk.namespace.Namespace;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

// TODO: clean up this class
public class TimeStampWriter extends AbstractXmlWriter<TimeStamp> {
    protected TimeStampWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(TimeStamp timeStamp) throws XMLStreamException {
        // should not be used
    }

    /*
    timestamps are special - they are elements with attribute of "timestamp", so the regular writer doesn't apply here
     */
    public void writeTimeStamp(
            final String localName,
            final TimeStamp timeStamp) throws XMLStreamException {
        writeTimeStamp(localName, timeStamp, ITM_TYPES);
    }

    public void writeTimeStamp(
            final String localName,
            final TimeStamp timeStamp,
            final Namespace namespace) throws  XMLStreamException {

        writeStartElement(localName, namespace);

        // REQUIRED
        writeAttribute("timestamp", timeStamp.getTimestamp().toXMLFormat());
        // OPTIONAL
        if (Objects.nonNull(timeStamp.getDescription())) {
            writeAttribute("description", timeStamp.getDescription());
        }

        writeEndElement(localName, namespace);
    }
}
