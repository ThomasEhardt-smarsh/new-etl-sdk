package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.MimeType;
import com.smarsh.dataengineering.model.TextObject;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class TextContentWriter extends AbstractXmlWriter<TextObject.TextContent> {
    protected TextContentWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(TextObject.TextContent textContent) throws XMLStreamException {
        writeStartElement("text-content", ITM);

        // attributes
        // OPTIONAL but always has a value
        writeAttribute("content-type", textContent.getContentType().value());
        // OPTIONAL but always has a value
        writeAttribute("indexable", String.valueOf(textContent.isIndexable()), ITM_TYPES);
        // OPTIONAL but always has a value
        writeAttribute("user-visible", textContent.getUserVisible().value());

        // value TODO: this needs to scale
        writeCharacters(textContent.getValue(), textContent.getContentType() != MimeType.TEXT_PLAIN);

        // elements
        // NONE


        writeEndElement("text-content", ITM);
    }
}
