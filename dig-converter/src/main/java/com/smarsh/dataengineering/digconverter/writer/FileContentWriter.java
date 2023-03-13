package com.smarsh.dataengineering.digconverter.writer;

import com.smarsh.dataengineering.model.FileBackedFileContent;
import com.smarsh.dataengineering.model.FileObject;
import com.smarsh.dataengineering.conversionsdk.writer.AbstractXmlWriter;
import com.smarsh.dataengineering.conversionsdk.util.AttachmentUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Objects;

import static com.smarsh.dataengineering.model.DigNamespaces.ITM;
import static com.smarsh.dataengineering.model.DigNamespaces.ITM_TYPES;

public class FileContentWriter extends AbstractXmlWriter<FileObject.FileContent> {
    protected FileContentWriter(XMLStreamWriter xmlStreamWriter) {
        super(xmlStreamWriter);
    }

    @Override
    public void write(FileObject.FileContent fileContent) throws XMLStreamException {
        writeStartElement("file-content", ITM);

        // attributes
        // OPTIONAL
        if (Objects.nonNull(fileContent.getFilePath())) {
            writeAttribute(
                    "file-path",
                    URLEncoder.encode(fileContent.getFilePath(), StandardCharsets.UTF_8)
            );
        }
        // OPTIONAL
        if (Objects.nonNull(fileContent.getName())) {
            writeAttribute("name", fileContent.getName());
        }
        // OPTIONAL
        if (Objects.nonNull(fileContent.getContentType())) {
            writeAttribute("content-type", fileContent.getContentType());
        }
        // OPTIONAL
        if (Objects.nonNull(fileContent.getEncoding())) {
            writeAttribute("encoding", fileContent.getEncoding().value());
        }
        // OPTIONAL but always has a value
        writeAttribute("indexable", String.valueOf(fileContent.isIndexable()), ITM_TYPES);
        // OPTIONAL but always has a value
        writeAttribute("user-visible", fileContent.getUserVisible().value());

        if (fileContent instanceof FileBackedFileContent x) {
            // buffer
            // first, convert to base64
            try {
                File base64File = Files.createTempFile(x.getName(), ".b64").toFile();
                base64File.deleteOnExit();
                AttachmentUtils.base64EncodeFile(x.getFileWithContents(), base64File);

                // stream the file
                try (var reader = new BufferedReader(new FileReader(base64File))) {
                    var buffer = new char[1024];
                    while (reader.read(buffer) != -1) {
                        writeCharacters(String.valueOf(buffer), false);
                    }
                }
            } catch (IOException e) {
                // could not create file
            }
        } else {
            // value
            writeCharacters(
                    Base64.getEncoder().encodeToString(fileContent.getValue()),
                    false
            );
        }

        writeEndElement("file-content", ITM);
    }
}
