package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.SecondaryAddress;
import com.smarsh.dataengineering.model.digxml.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.util.Objects;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class UserInfoWriter extends AbstractXmlWriter<UserInfo> {
    protected UserInfoWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo userInfo) throws XMLStreamException {
        writeStartElement("user-info", ITM);

        // attributes
        // none

        // elements
        // OPTIONAL
        if (Objects.nonNull(userInfo.getUserId())) {
            writeElementWithPlainText("user-id", userInfo.getUserId(), ITM_TYPES);
        }
        // REQUIRED
        writeElementWithPlainText("user-type", userInfo.getUserType(), ITM_TYPES);
        // OPTIONAL
        if (Objects.nonNull(userInfo.getName())) {
            new NameTypeWriter(xmlStreamWriter).write(userInfo.getName());
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getEmailAddress())) {
            writeElementWithPlainText("email-address", userInfo.getEmailAddress(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getAffiliation())) {
            new AffiliationWriter(xmlStreamWriter).write(userInfo.getAffiliation());
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getGeoLocation())) {
            new GeoLocationWriter(xmlStreamWriter).write(userInfo.getGeoLocation());
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getPhoneNumbers())) {
            new PhoneNumbersWriter(xmlStreamWriter).write(userInfo.getPhoneNumbers());
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getSecondaryAddresses()) && !userInfo.getSecondaryAddresses().getSecondaryAddress().isEmpty()) {
            writeStartElement("secondary-addresses", ITM_TYPES);

            var secondaryAddressWriter = new SecondaryAddressWriter(xmlStreamWriter);
            for (SecondaryAddress secondaryAddress : userInfo.getSecondaryAddresses().getSecondaryAddress()) {
                secondaryAddressWriter.write(secondaryAddress);
            }
            writeEndElement("secondary-addresses", ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getGroups()) && !userInfo.getGroups().getGroup().isEmpty()) {
            writeStartElement("groups", ITM_TYPES);

            for (String group : userInfo.getGroups().getGroup()) {
                writeElementWithPlainText("group", group, ITM_TYPES);
            }

            writeEndElement("groups", ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getImage())) {
            new ImageWriter(xmlStreamWriter).write(userInfo.getImage());
        }
        // OPTIONAL
        if (Objects.nonNull(userInfo.getModificationTime())) {
            new TimeStampWriter(xmlStreamWriter).writeTimeStamp(
                    "modification-time",
                    userInfo.getModificationTime(),
                    ITM_TYPES);
        }

        writeEndElement("user-info", ITM);
    }
}
