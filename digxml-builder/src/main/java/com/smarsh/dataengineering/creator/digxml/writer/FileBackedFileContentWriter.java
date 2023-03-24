package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.model.digxml.FileBackedFileContent;

import javax.xml.stream.XMLStreamWriter;

public class FileBackedFileContentWriter extends FileContentWriter {
    protected FileBackedFileContentWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    public void write(FileBackedFileContent content) {

    }
}
