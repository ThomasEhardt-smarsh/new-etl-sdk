package com.smarsh.dataengineering.model;

import com.smarsh.dataengineering.model.FileObject.FileContent;

import java.io.File;

public class FileBackedFileContent extends FileContent {
    protected File fileWithContents;

    public File getFileWithContents() {
        return fileWithContents;
    }

    public void setFileWithContents(File fileWithContents) {
        this.fileWithContents = fileWithContents;
    }
}
