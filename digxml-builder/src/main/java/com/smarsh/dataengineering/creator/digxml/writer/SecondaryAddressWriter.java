package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.SecondaryAddress;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class SecondaryAddressWriter extends AbstractXmlWriter<SecondaryAddress> {
    protected SecondaryAddressWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(SecondaryAddress secondaryAddress) throws XMLStreamException {
        writeStartElement("secondary-address", ITM_TYPES);


        // attributes
        // REQUIRED
        writeAttribute("address-type", secondaryAddress.getAddressType());

        if (Objects.nonNull(secondaryAddress.getValue())) {
            writeCharacters(secondaryAddress.getValue(), false);
        }


        writeEndElement("secondary-address", ITM_TYPES);
    }
}
