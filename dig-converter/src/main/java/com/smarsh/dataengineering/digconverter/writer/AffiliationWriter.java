package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class AffiliationWriter extends AbstractXmlWriter<UserInfo.Affiliation> {
    protected AffiliationWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo.Affiliation affiliation) throws XMLStreamException {
        writeStartElement("affiliation", ITM_TYPES);

        // attributes
        // NONE

        // elements
        // OPTIONAL
        if (Objects.nonNull(affiliation.getEmployeeId())) {
            writeElementWithPlainText("employee-id", affiliation.getEmployeeId(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(affiliation.getTitle())) {
            writeElementWithPlainText("title", affiliation.getTitle(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(affiliation.getDepartment())) {
            writeElementWithPlainText("department", affiliation.getDepartment(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(affiliation.getDivision())) {
            writeElementWithPlainText("division", affiliation.getDivision(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(affiliation.getCompany())) {
            writeElementWithPlainText("company", affiliation.getCompany(), ITM_TYPES);
        }
        // OPTIONAL
        if (Objects.nonNull(affiliation.getBuilding())) {
            writeElementWithPlainText("building", affiliation.getBuilding(), ITM_TYPES);
        }


        writeEndElement("affiliation", ITM_TYPES);
    }
}
