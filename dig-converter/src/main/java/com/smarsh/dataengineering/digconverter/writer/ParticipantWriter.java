package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.Interaction;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;

public class ParticipantWriter extends AbstractXmlWriter<Interaction.Participants.Participant> {
    protected ParticipantWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(Interaction.Participants.Participant participant) throws XMLStreamException {
        writeStartElement("participant", ITM);

        // attributes
        // REQUIRED
        writeAttribute("participant-id", participant.getParticipantId());
        // OPTIONAL
        if (Objects.nonNull(participant.getParticipantRole())) {
            xmlStreamWriter.writeAttribute("participant-role", participant.getParticipantRole());
        }

        // elements
        // REQUIRED
        new NetworkEndpointWriter(xmlStreamWriter).write(participant.getNetworkInfo());
        // REQUIRED
        new UserInfoWriter(xmlStreamWriter).write(participant.getUserInfo());

        writeEndElement("participant", ITM);
    }
}
