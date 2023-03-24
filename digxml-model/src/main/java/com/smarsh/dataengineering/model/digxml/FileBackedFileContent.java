package com.smarsh.dataengineering.model.digxml;

import java.io.File;

public class FileBackedFileContent extends FileObject.FileContent {
    protected File fileWithContents;

    public File getFileWithContents() {
        return fileWithContents;
    }

    public void setFileWithContents(File fileWithContents) {
        this.fileWithContents = fileWithContents;
    }
}
