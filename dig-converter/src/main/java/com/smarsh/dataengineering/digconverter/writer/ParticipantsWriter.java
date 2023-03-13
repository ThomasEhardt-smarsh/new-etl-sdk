package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.Interaction;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;

public class ParticipantsWriter extends AbstractXmlWriter<Interaction.Participants> {

    protected ParticipantsWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(Interaction.Participants participants) throws XMLStreamException {
        xmlStreamWriter.writeStartElement(ITM.namespaceURI(), "participants");

        for (Interaction.Participants.Participant participant : participants.getParticipant()) {
            new ParticipantWriter(xmlStreamWriter).write(participant);
        }


        // </participants>
        xmlStreamWriter.writeEndElement();
    }
}
