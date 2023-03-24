package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.UserInfo;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_TYPES;

public class AffiliationWriter extends AbstractXmlWriter<UserInfo.Affiliation> {
    protected AffiliationWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(UserInfo.Affiliation affiliation) throws XMLStreamException {
        var employeeId = affiliation.getEmployeeId();
        var title = affiliation.getTitle();
        var department = affiliation.getDepartment();
        var division = affiliation.getDivision();
        var company = affiliation.getCompany();
        var building = affiliation.getBuilding();

        var haveAnythingToWrite = Stream.of(employeeId, title, department, division, company, building).anyMatch(Objects::nonNull);

        if (haveAnythingToWrite) {

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
        } else {
            // all values are optional - don't write empty element
        }
    }
}
