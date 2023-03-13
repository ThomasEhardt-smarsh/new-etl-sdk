package com.smarsh.dataengineering.conversionsdk.writer;

import com.smarsh.dataengineering.conversionsdk.namespace.Namespace;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public abstract class AbstractXmlWriter<T> {
    protected final XMLStreamWriter xmlStreamWriter;

    public abstract void write(T t) throws XMLStreamException;

    Deque<ElementTuple<String, Namespace>> elementTupleDeque = new ArrayDeque<>();

    protected AbstractXmlWriter(XMLStreamWriter xmlStreamWriter) {
        this.xmlStreamWriter = xmlStreamWriter;
    }

    protected void writeElementWithPlainText(final String localName, final String value, final Namespace namespace) throws XMLStreamException {
        writeStartElement(localName, namespace);
        writeCharacters(value);
        writeEndElement(localName, namespace);
    }
    protected void writeStartElement(final String localName, final Namespace namespace) throws XMLStreamException {
        xmlStreamWriter.writeStartElement(namespace.localName(), localName, namespace.namespaceURI());

        elementTupleDeque.push(new ElementTuple<>(localName, namespace));
    }

    protected void writeEndElement(final String ignoredLocalName, final Namespace ignoredNamespace) throws XMLStreamException {
        xmlStreamWriter.writeEndElement();

        ElementTuple<String, Namespace> topElementTuple = elementTupleDeque.peek();
        if (Objects.nonNull(topElementTuple) && topElementTuple.equals(new ElementTuple<>(ignoredLocalName, ignoredNamespace))) {
            elementTupleDeque.pop();
        }
    }

    protected void writeEmptyElement(final String localName, final Namespace namespace) throws XMLStreamException {
        xmlStreamWriter.writeEmptyElement(namespace.namespaceURI(), localName);
    }

    protected void writeAttribute(final String localName, final String value) throws XMLStreamException {
        writeAttribute(localName, value, (Namespace) null);
    }

    protected void writeAttribute(final String localName, final String value, final Namespace namespace) throws XMLStreamException {
        if (Objects.nonNull(namespace)) {
            xmlStreamWriter.writeAttribute(namespace.namespaceURI(), localName, value);
        } else {
            xmlStreamWriter.writeAttribute(localName, value);
        }
    }

    protected void writeNamespace(final Namespace namespace) throws XMLStreamException {
        xmlStreamWriter.writeNamespace(namespace.localName(), namespace.namespaceURI());
    }


    /**
     * write characters out to the stream
     * @param value
     * @throws XMLStreamException
     */
    private void writeCharacters(final String value) throws XMLStreamException {
        xmlStreamWriter.writeCharacters(value);
    }

    private void writeCData(final String value) throws XMLStreamException {
        xmlStreamWriter.writeCData(value);
    }

    protected void writeCharacters(final String value, boolean isCData) throws XMLStreamException {
        if (isCData) {
            writeCData(value);
        } else {
            writeCharacters(value);
        }
    }

    record ElementTuple<X, Y>(X x, Y y) {

        @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }

                if (Objects.isNull(o) || this.getClass() != o.getClass()) {
                    return false;
                }

                var oo = ((ElementTuple<?, ?>) o);

                return Objects.equals(this.x, oo.x) && (Objects.equals(this.y, oo.y));
            }
        }
}
