package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Encoding;
import com.smarsh.dataengineering.model.FileBackedFileContent;
import com.smarsh.dataengineering.model.UserVisibility;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public class FileBackedFileContentBuilder implements AbstractBuilder<FileBackedFileContentBuilder, FileBackedFileContent> {
    @Nullable
    protected File fileWithContents;
    @Nullable
    protected byte[] value;
    @Nullable
    protected String filePath;
    @Nullable
    protected String name;
    @Nullable
    protected String contentType;
    @Nullable
    protected Encoding encoding;
    @Nullable
    protected Boolean indexable;
    @Nullable
    protected UserVisibility userVisibility;

    @Override
    public FileBackedFileContent build() {
        var fileContent = new FileBackedFileContent();

        // attributes
        // OPTIONAL
        Optional.ofNullable(filePath).ifPresent(fileContent::setFilePath);
        // OPTIONAL
        Optional.ofNullable(name).ifPresent(fileContent::setName);
        // OPTIONAL
        Optional.ofNullable(contentType).ifPresent(fileContent::setContentType);
        // OPTIONAL
        Optional.ofNullable(encoding).ifPresent(fileContent::setEncoding);
        // OPTIONAL
        Optional.ofNullable(indexable).ifPresent(fileContent::setIndexable);
        // OPTIONAL
        Optional.ofNullable(userVisibility).ifPresent(fileContent::setUserVisible);

        // elements

        // value
        Optional.ofNullable(fileWithContents).ifPresent(fileContent::setFileWithContents);

        return fileContent;
    }

    public void setValue(@NotNull byte[] value) {
        this.value = value;
    }

    public void setFilePath(@NotNull String filePath) {
        this.filePath = filePath;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setContentType(@NotNull String contentType) {
        this.contentType = contentType;
    }

    public void setEncoding(@NotNull Encoding encoding) {
        this.encoding = encoding;
    }

    public void setIndexable(@NotNull Boolean indexable) {
        this.indexable = indexable;
    }

    public void setUserVisibility(@NotNull UserVisibility userVisibility) {
        this.userVisibility = userVisibility;
    }

    public void setFileWithContents(@NotNull File fileWithContents) {
        this.fileWithContents = fileWithContents;
    }
}
