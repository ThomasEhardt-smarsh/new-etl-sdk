package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.TimeFrame;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;

public class TimeFrameWriter extends AbstractXmlWriter<TimeFrame> {
    protected TimeFrameWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(TimeFrame timeFrame) throws XMLStreamException {
        var timeStampWriter = new TimeStampWriter(xmlStreamWriter);

        writeStartElement("time-frame", ITM);


        timeStampWriter.writeTimeStamp("start-time", timeFrame.getStartTime());
        timeStampWriter.writeTimeStamp("end-time", timeFrame.getEndTime());
        writeEndElement("time-frame", ITM);
    }
}
