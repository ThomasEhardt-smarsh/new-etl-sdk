package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.NetworkEndpoint;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class NetworkEndpointWriter extends AbstractXmlWriter<NetworkEndpoint> {
    protected NetworkEndpointWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(NetworkEndpoint networkEndpoint) throws XMLStreamException {
        writeStartElement("network-info", ITM);

        // attributes
        // OPTIONAL
        if (Objects.nonNull(networkEndpoint.getEndpointIdType())) {
            writeAttribute("endpoint-id-type", networkEndpoint.getEndpointIdType());
        }

        // elements
        // REQUIRED
        writeElementWithPlainText("network", networkEndpoint.getNetwork(), ITM_TYPES);
        // REQUIRED
        writeElementWithPlainText("endpoint-id", networkEndpoint.getEndpointId(), ITM_TYPES);
        // OPTIONAL
        if (Objects.nonNull(networkEndpoint.getDisplayName())) {
            writeElementWithPlainText("display-name", networkEndpoint.getDisplayName(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(networkEndpoint.getGroups()) && !networkEndpoint.getGroups().getGroup().isEmpty()) {
            writeStartElement("groups", ITM_TYPES);

            for (String group : networkEndpoint.getGroups().getGroup()) {
                writeElementWithPlainText("group", group, ITM_TYPES);
            }

            writeEndElement("groups", ITM_TYPES);
        }

        writeEndElement("network-info", ITM);
    }
}
