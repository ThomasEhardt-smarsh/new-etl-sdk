package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.Modality;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class ModalityWriter extends AbstractXmlWriter<Modality> {
    protected ModalityWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(Modality modality) throws XMLStreamException {
        writeStartElement("modality", ITM);

        // attributes
        // OPTIONAL
        if (Objects.nonNull(modality.getClassification())) {
            writeAttribute("classification", modality.getClassification().value());
        }
        // OPTIONAL
        if (Objects.nonNull(modality.getType())) {
            writeAttribute("type", modality.getType().value());
        }
        // OPTIONAL
        if (Objects.nonNull(modality.getVendor())) {
            writeAttribute("vendor", modality.getVendor());
        }
        // OPTIONAL
        if (Objects.nonNull(modality.getDescription())) {
            writeAttribute("description", modality.getDescription());
        }

        // elements
        // REQUIRED
        writeElementWithPlainText("channel", modality.getChannel(), ITM_TYPES);
        // REQUIRED
        writeElementWithPlainText("network", modality.getNetwork(), ITM_TYPES);

        writeEmptyElement("modality", ITM);
    }
}
