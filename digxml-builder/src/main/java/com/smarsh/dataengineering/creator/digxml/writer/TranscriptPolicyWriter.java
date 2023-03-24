package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.DigNamespaces;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy.Retention;
import com.smarsh.dataengineering.model.digxml.TranscriptPolicy.Retention.Policy;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.util.Objects;
import java.util.Optional;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM_POL;

public class TranscriptPolicyWriter extends AbstractXmlWriter<TranscriptPolicy> {

    protected TranscriptPolicyWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(TranscriptPolicy transcriptPolicy) throws XMLStreamException {
        writeStartElement("policy", ITM_POL);

        new RetentionWriter(xmlStreamWriter).write(transcriptPolicy.getRetention());
        writeElementWithPlainText("disposition", transcriptPolicy.getDisposition(), ITM_POL);

        writeEndElement("policy", ITM_POL);
    }

    public static class RetentionWriter extends AbstractXmlWriter<Retention> {

        protected RetentionWriter(XMLStreamWriter xmlStreamWriter) {
            super(xmlStreamWriter);
        }

        @Override
        public void write(@NotNull Retention retention) throws XMLStreamException {
            writeStartElement("retention", ITM_POL);

            if (Objects.nonNull(retention.getPolicy()) && !retention.getPolicy().isEmpty()) {
                for (Policy policy : retention.getPolicy()) {
                    new PolicyWriter(xmlStreamWriter).write(policy);
                }
            }

            writeEndElement("retention", ITM_POL);
        }
    }

    public static class PolicyWriter extends AbstractXmlWriter<Policy> {

        protected PolicyWriter(XMLStreamWriter xmlStreamWriter) {
            super(xmlStreamWriter);
        }

        @Override
        public void write(Policy policy) throws XMLStreamException {
            writeEmptyElement("policy", ITM_POL);

            writeAttribute("action", policy.getAction());

            String decription = policy.getDecription();
            if (Objects.nonNull(decription)) {
                // NOTE: this misspelling is in the XSD
                writeAttribute("decription", decription);
            }

            String id = policy.getId();
            if (Objects.nonNull(id)) {
                writeAttribute("id", id);
            }
        }
    }
}
