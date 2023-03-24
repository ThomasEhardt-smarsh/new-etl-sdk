package com.smarsh.dataengineering.conversionsdk.writer;

import com.smarsh.dataengineering.conversionsdk.namespace.Namespace;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractXmlWriterTest {
    private static final Namespace NAMESPACE = new Namespace("local-name", "http://example.com/local-name");

    StringWriter outputWriter;
    XMLStreamWriter xmlStreamWriter;
    XmlWriterTestImpl xmlWriter;
    private static final String elementName = "name";
    private static final String attributeName = "attribute";
    private static final String attributeValue = "value";
    private static final String TEXT_STRING = "hello, world";

    @BeforeEach
    void setup() throws Exception {
        outputWriter = new StringWriter();
        xmlStreamWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(outputWriter);

        // set the prefix first
        xmlStreamWriter.setPrefix(NAMESPACE.localName(), NAMESPACE.namespaceURI());

        xmlWriter = new XmlWriterTestImpl(xmlStreamWriter);
    }

    @AfterEach
    void teardown() throws Exception {
        xmlStreamWriter.flush();
        xmlStreamWriter.close();

        outputWriter.flush();
        outputWriter.close();
    }

    @Test
    void writeElementWithPlainText() throws Exception {
        xmlWriter.writeElementWithPlainText(elementName, TEXT_STRING, NAMESPACE);

        assertEquals(
                "<%s:%s>%s</%s:%s>".formatted(
                        NAMESPACE.localName(),
                        elementName,
                        TEXT_STRING,
                        NAMESPACE.localName(),
                        elementName
                ), outputWriter.toString()
        );
    }

    @Test
    void writeStartElement() throws Exception {
        xmlWriter.writeStartElement(elementName, NAMESPACE);

        // by default, this won't write the closing tag until we tell it to
        assertEquals("<%s:%s".formatted(NAMESPACE.localName(), elementName), outputWriter.toString());
    }

    @Test
    void writeEndElement() throws Exception {
        // we must first write out a start element
        xmlWriter.writeStartElement(elementName, NAMESPACE);
        xmlWriter.writeEndElement(elementName, NAMESPACE);

        assertEquals(
                "<%s:%s></%s:%s>".formatted(
                    NAMESPACE.localName(),
                    elementName,
                    NAMESPACE.localName(),
                    elementName
                ),
                outputWriter.toString()
        );
    }

    @Test
    void writeEmptyElement() throws Exception {
        xmlWriter.writeEmptyElement(elementName, NAMESPACE);

        // a hack to force the entire thing to be written out
        xmlStreamWriter.writeEndDocument();

        assertEquals(
                "<%s:%s/>".formatted(
                        NAMESPACE.localName(),
                        elementName
                ),
                outputWriter.toString()
        );
    }

    @Test
    void writeAttribute() throws Exception {
        xmlWriter.writeStartElement(elementName, NAMESPACE);
        xmlWriter.writeAttribute(attributeName, attributeValue);
        xmlWriter.writeEndElement(elementName, NAMESPACE);

        assertEquals(
                "<%s:%s %s=\"%s\"></%s:%s>".formatted(
                        NAMESPACE.localName(),
                        elementName,
                        attributeName,
                        attributeValue,
                        NAMESPACE.localName(),
                        elementName
                ),
                outputWriter.toString()
        );
    }

    @Test
    void writeAttributeWithNamespace() throws Exception {
        xmlWriter.writeStartElement(elementName, NAMESPACE);
        xmlWriter.writeAttribute(attributeName, attributeValue, NAMESPACE);
        xmlWriter.writeEndElement(elementName, NAMESPACE);

        assertEquals(
                "<%s:%s %s:%s=\"%s\"></%s:%s>".formatted(
                        NAMESPACE.localName(),
                        elementName,
                        NAMESPACE.localName(),
                        attributeName,
                        attributeValue,
                        NAMESPACE.localName(),
                        elementName
                ),
                outputWriter.toString()
        );
    }

    @Test
    void writeNamespace() throws Exception {
        xmlStreamWriter.writeStartDocument();
        xmlWriter.writeStartElement(elementName, NAMESPACE);
        xmlWriter.writeNamespace(NAMESPACE);
        xmlWriter.writeEndElement(elementName, NAMESPACE);
        xmlStreamWriter.writeEndDocument();

        assertEquals(
                "<?xml version=\"1.0\" ?><%s:%s xmlns:%s=\"%s\"></%s:%s>".formatted(
                        NAMESPACE.localName(),
                        elementName,
                        NAMESPACE.localName(),
                        NAMESPACE.namespaceURI(),
                        NAMESPACE.localName(),
                        elementName
                ),
                outputWriter.toString()
        );
    }

    @Test
    void writeCharactersNotCDATA() throws Exception {
        xmlWriter.writeStartElement(elementName, NAMESPACE);
        xmlWriter.writeCharacters(TEXT_STRING, false);
        xmlWriter.writeEndElement(elementName, NAMESPACE);

        assertEquals(
                "<%s:%s>%s</%s:%s>".formatted(
                        NAMESPACE.localName(),
                        elementName,
                        TEXT_STRING,
                        NAMESPACE.localName(),
                        elementName
                ),
                outputWriter.toString()
        );
    }

    @Test
    void writeCharactersCDATA() throws Exception {
        xmlWriter.writeStartElement(elementName, NAMESPACE);
        xmlWriter.writeCharacters(TEXT_STRING, true);
        xmlWriter.writeEndElement(elementName, NAMESPACE);

        assertEquals(
                "<%s:%s><![CDATA[%s]]></%s:%s>".formatted(
                        NAMESPACE.localName(),
                        elementName,
                        TEXT_STRING,
                        NAMESPACE.localName(),
                        elementName
                ),
                outputWriter.toString()
        );
    }

    static class XmlWriterTestImpl extends AbstractXmlWriter<String> {
        protected XmlWriterTestImpl(XMLStreamWriter xmlStreamWriter) {
            super(xmlStreamWriter);
        }

        @Override
        public void write(String s) throws XMLStreamException {

        }
    }
}