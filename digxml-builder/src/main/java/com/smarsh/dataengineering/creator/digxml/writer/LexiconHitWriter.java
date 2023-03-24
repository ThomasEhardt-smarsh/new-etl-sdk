package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.model.digxml.LexiconHit;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.util.Objects;
import java.util.Optional;

import static com.smarsh.dataengineering.model.digxml.DigNamespaces.ITM;

public class LexiconHitWriter extends AbstractXmlWriter<LexiconHit> {
    protected LexiconHitWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(LexiconHit lexiconHit) throws XMLStreamException {
        writeStartElement("lexicon-hit", ITM);

        writeAttribute("match-type", lexiconHit.getMatchType());

        var nearDistance = lexiconHit.getNearDistance();
        if (Objects.nonNull(nearDistance)) {
            writeAttribute("near-distance", nearDistance.toString(10));
        }

        // NOTE: this is marked as optional but always has a value
        var caseSensitive = lexiconHit.isCaseSensitive();
        writeAttribute("case-sensitive", String.valueOf(caseSensitive));

        // NOTE: this is marked as optional but always has a value
        var plainText = lexiconHit.isPlainText();
        writeAttribute("plain-text", String.valueOf(plainText));

        var offset = lexiconHit.getOffset();
        if (Objects.nonNull(offset)) {
            writeAttribute("offset", String.valueOf(offset));
        }

        var length = lexiconHit.getLength();
        if (Objects.nonNull(length)) {
            writeAttribute("length", length.toString(10));
        }

        var origin = lexiconHit.getOrigin();
        if (Objects.nonNull(origin)) {
            writeAttribute("origin", origin);
        }

        var category = lexiconHit.getCategory();
        if (Objects.nonNull(category)) {
            writeAttribute("category", category);
        }

        var policy = lexiconHit.getPolicy();
        if (Objects.nonNull(policy)) {
            writeAttribute("policy", policy);
        }

        var value = lexiconHit.getValue();
        if (Objects.nonNull(value)) {
            writeCharacters(value, false);
        }

        writeEndElement("lexicon-hit", ITM);
    }
}
