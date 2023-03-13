package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Base64;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class ImageWriter extends AbstractXmlWriter<UserInfo.Image> {
    protected ImageWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo.Image image) throws XMLStreamException {
        writeStartElement("image", ITM_TYPES);

        // attributes
        // REQUIRED
        writeAttribute("type", image.getType());

        // value
        writeCharacters(
                Base64.getEncoder().encodeToString(image.getValue()),
                false
        );

        writeEndElement("image", ITM_TYPES);
    }
}
