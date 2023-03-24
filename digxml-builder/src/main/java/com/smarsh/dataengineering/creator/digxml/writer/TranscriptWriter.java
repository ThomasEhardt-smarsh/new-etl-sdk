package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.Transcript;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.*;

public class TranscriptWriter extends AbstractXmlWriter<Transcript> {
    public TranscriptWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(Transcript transcript) throws XMLStreamException {
        // write our transcript element
        writeStartElement("transcript", ITM);

        // write the namespaces
        writeNamespace(ITM);
        writeNamespace(ITM_TYPES);
        writeNamespace(ITM_POL);

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

        // OPTIONAL
        var transcriptPolicy = transcript.getPolicy();
        if (Objects.nonNull(transcriptPolicy)) {
            new TranscriptPolicyWriter(xmlStreamWriter).write(transcriptPolicy);
        }

        writeEndElement("transcript", ITM);
    }
}
