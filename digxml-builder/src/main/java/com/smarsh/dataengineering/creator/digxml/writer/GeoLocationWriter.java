package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class GeoLocationWriter extends AbstractXmlWriter<UserInfo.GeoLocation> {
    protected GeoLocationWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo.GeoLocation geoLocation) throws XMLStreamException {
        var city = geoLocation.getCity();
        var state = geoLocation.getState();
        var country = geoLocation.getCountry();

        var haveAnythingToWrite = Stream.of(city, state, country).anyMatch(Objects::nonNull);

        if (haveAnythingToWrite) {
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
        } else {
            // all values are optional - don't write empty element
        }
    }
}
