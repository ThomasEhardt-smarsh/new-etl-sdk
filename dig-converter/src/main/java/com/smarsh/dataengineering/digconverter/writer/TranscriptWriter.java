package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.Transcript;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class TranscriptWriter extends AbstractXmlWriter<Transcript> {
    public TranscriptWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(Transcript transcript) throws XMLStreamException {
        // TODO: determine if this really should be done here or not
        xmlStreamWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");

        // write our transcript element
        writeStartElement("transcript", ITM);

        // write the namespaces
        writeNamespace(ITM);
        writeNamespace(ITM_TYPES);

        // finish the transcript start tag with attributes
        // REQUIRED
        writeAttribute("id", transcript.getId());
        // REQUIRED
        writeAttribute("source-endpoint-id", transcript.getSourceEndpointId());
        // OPTIONAL
        var sourceEndpointVersion = transcript.getSourceEndpointVersion();
        if (Objects.nonNull(sourceEndpointVersion)) {
            writeAttribute("source-endpoint-version", transcript.getSourceEndpointVersion());
        }
        // REQUIRED
        writeAttribute("time-stamp", transcript.getTimeStamp().toXMLFormat());

        // now write the interaction
        new InteractionWriter(xmlStreamWriter).write(transcript.getInteraction());

        writeEndElement("transcript", ITM);

        xmlStreamWriter.writeEndDocument();
    }
}
