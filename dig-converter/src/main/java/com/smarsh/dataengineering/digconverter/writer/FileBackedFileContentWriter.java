package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.FileBackedFileContent;

import javax.xml.stream.XMLStreamWriter;

public class FileBackedFileContentWriter extends FileContentWriter {
    protected FileBackedFileContentWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    public void write(FileBackedFileContent content) {

    }
}
