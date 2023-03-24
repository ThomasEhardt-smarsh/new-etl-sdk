package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.IndexableAttributes;
import com.smarsh.dataengineering.model.digxml.MimeType;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class IndexableAttributeWriter extends AbstractXmlWriter<IndexableAttributes.Attribute> {
    protected IndexableAttributeWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(IndexableAttributes.Attribute attribute) throws XMLStreamException {

        var attributeValue = attribute.getValue();
        var hasAttributeValue = Objects.nonNull(attributeValue) && !attributeValue.trim().isEmpty();

        if (hasAttributeValue) {
            writeStartElement("attribute", ITM_TYPES);
        } else {
            writeEmptyElement("attribute", ITM_TYPES);
        }

        // attributes
        // REQUIRED
        writeAttribute("name", attribute.getName());
        // OPTIONAL
        if (Objects.nonNull(attribute.getContentType())) {
            writeAttribute("content-type", attribute.getContentType().value());
        }
        // OPTIONAL but always has a value
        writeAttribute("indexable", String.valueOf(attribute.isIndexable()), ITM_TYPES);
        // OPTIONAL but always has a value
        writeAttribute("user-visible", attribute.getUserVisible().value());
        // OPTIONAL but always has a value (TODO: verify this)
        writeAttribute("classification", attribute.getClassification());
        // OPTIONAL but always has a value
        writeAttribute("system-flag", String.valueOf(attribute.isSystemFlag()));


        if (hasAttributeValue) {
            writeCharacters(attributeValue, attribute.getContentType() != MimeType.TEXT_PLAIN);
            writeEndElement("attribute", ITM_TYPES);
        }
    }
}
