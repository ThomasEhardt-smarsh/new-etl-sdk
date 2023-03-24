package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedFileContentTest {

    @TempDir
    static
    Path tempDirPath;
    
    private static File inputFile;
    private static final String TEST_DATA = "Jello, Whirled";

    @BeforeAll
    static void setInputFile() throws Exception {
        var inputFile = tempDirPath.resolve("getFileWithContents.txt").toFile();
        Files.writeString(inputFile.toPath(), TEST_DATA);
    }

    @Test
    void getFileWithContents() throws Exception {
        var fileBackedContent = new FileBackedFileContent();
        fileBackedContent.setFileWithContents(inputFile);

        assertEquals(inputFile, fileBackedContent.getFileWithContents());
    }

    @Test
    void setFileWithContents() throws Exception {
        var fileBackedContent = new FileBackedFileContent();
        fileBackedContent.setFileWithContents(inputFile);

        assertEquals(inputFile, fileBackedContent.fileWithContents);
    }
}