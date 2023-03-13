package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class GeoLocationWriter extends AbstractXmlWriter<UserInfo.GeoLocation> {
    protected GeoLocationWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo.GeoLocation geoLocation) throws XMLStreamException {
        writeStartElement("geo-location", ITM_TYPES);

        // attributes
        // NONE

        // elements
        // OPTIONAL
        if (Objects.nonNull(geoLocation.getCity())) {
            writeElementWithPlainText("city", geoLocation.getCity(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(geoLocation.getState())) {
            writeElementWithPlainText("state", geoLocation.getState(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(geoLocation.getCity())) {
            writeElementWithPlainText("country", geoLocation.getCountry(), ITM_TYPES);
        }

        writeEndElement("geo-location", ITM_TYPES);
    }
}
