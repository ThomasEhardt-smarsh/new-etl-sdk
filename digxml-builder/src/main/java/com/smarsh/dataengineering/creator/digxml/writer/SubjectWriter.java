package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.Subject;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class SubjectWriter extends AbstractXmlWriter<Subject> {
    protected SubjectWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(@NotNull Subject subject) throws XMLStreamException {
        // all elements are optional
        var value = subject.getValue();
        var contentType = subject.getContentType();

        var haveAnythingToWrite = Stream.of(value, contentType).anyMatch(Objects::nonNull);

        if (haveAnythingToWrite) {
            writeStartElement("subject", ITM_TYPES);

            if (Objects.nonNull(contentType)) {
                writeAttribute("content-type", contentType.value());
            }

            if (Objects.nonNull(value)) {
                writeCharacters(value, false);
            }

            writeEndElement("subject", ITM_TYPES);
        } else {
            // all values are optional - don't write empty element
        }
    }
}
