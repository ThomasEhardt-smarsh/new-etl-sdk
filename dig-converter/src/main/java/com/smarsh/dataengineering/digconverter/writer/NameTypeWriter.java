package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.NameType;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class NameTypeWriter extends AbstractXmlWriter<NameType> {
    protected NameTypeWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(NameType nameType) throws XMLStreamException {
        // we may not have a displayName since it's optional
        var displayName = nameType.getDisplayName();
        var haveDisplayName = Objects.nonNull(displayName) && !displayName.trim().isEmpty();

        if (haveDisplayName) {
            writeStartElement("name", ITM_TYPES);
            writeElementWithPlainText("display-name", displayName, ITM_TYPES);
            writeEndElement("name", ITM_TYPES);
        } else {
            // TODO: should we write out an empty element?
            // nothing to do
        }
    }
}
