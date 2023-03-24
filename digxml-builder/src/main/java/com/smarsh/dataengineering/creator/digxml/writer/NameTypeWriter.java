package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.NameType;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class NameTypeWriter extends AbstractXmlWriter<NameType> {
    protected NameTypeWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(NameType nameType) throws XMLStreamException {
        // all elements are optional
        var first = nameType.getFirst();
        var middle = nameType.getMiddle();
        var last = nameType.getLast();
        var initials = nameType.getInitials();
        var displayName = nameType.getDisplayName();

        var haveAnythingToWrite = Stream.of(first, middle, last, initials, displayName).anyMatch(Objects::nonNull);

        if (haveAnythingToWrite) {
            writeStartElement("name", ITM_TYPES);

            if (Objects.nonNull(first)) {
                writeElementWithPlainText("first", first, ITM_TYPES);
            }
            if (Objects.nonNull(middle)) {
                writeElementWithPlainText("middle", middle, ITM_TYPES);
            }
            if (Objects.nonNull(last)) {
                writeElementWithPlainText("last", last, ITM_TYPES);
            }
            if (Objects.nonNull(initials)) {
                writeElementWithPlainText("initials", initials, ITM_TYPES);
            }
            if (Objects.nonNull(displayName)) {
                writeElementWithPlainText("display-name", displayName, ITM_TYPES);
            }

            writeEndElement("name", ITM_TYPES);
        } else {
            // all values are optional - don't write empty element
        }
    }
}
