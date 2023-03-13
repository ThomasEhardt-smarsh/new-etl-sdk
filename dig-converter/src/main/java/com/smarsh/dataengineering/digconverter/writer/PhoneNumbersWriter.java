package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class PhoneNumbersWriter extends AbstractXmlWriter<UserInfo.PhoneNumbers> {
    protected PhoneNumbersWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo.PhoneNumbers phoneNumbers) throws XMLStreamException {
        writeStartElement("phone-numbers", ITM_TYPES);

        // attributes
        // NONE

        // elements
        // OPTIONAL
        if (Objects.nonNull(phoneNumbers.getOfficePhone())) {
            writeElementWithPlainText("office-phone", phoneNumbers.getOfficePhone(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(phoneNumbers.getSecondaryOfficePhone())) {
            writeElementWithPlainText("secondary-office-phone", phoneNumbers.getSecondaryOfficePhone(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(phoneNumbers.getMobilePhone())) {
            writeElementWithPlainText("mobile-phone", phoneNumbers.getMobilePhone(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(phoneNumbers.getHomePhone())) {
            writeElementWithPlainText("home-phone", phoneNumbers.getHomePhone(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(phoneNumbers.getOtherPhone())) {
            writeElementWithPlainText("other-phone", phoneNumbers.getOtherPhone(), ITM_TYPES);
        }


        writeEndElement("phone-numbers", ITM_TYPES);
    }
}
