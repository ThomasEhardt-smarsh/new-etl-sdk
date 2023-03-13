package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Action;
import com.smarsh.dataengineering.model.FileEvent;
import com.smarsh.dataengineering.model.FileEventType;
import com.smarsh.dataengineering.model.FileObject;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FileEventBuilder implements AbstractBuilder<FileEventBuilder, FileEvent> {
    @NotNull
    private final FileObject fileObject;
    @NotNull
    private final String fileEventId;
    @NotNull
    private final FileEventType fileEventType;

    @Nullable
    private Action action;
    @Nullable
    private Boolean historyFlag;
    @Nullable
    private String cocHashValue;


    public FileEventBuilder(
            @NotNull FileObject fileObject,
            @NotNull String fileEventId,
            @NotNull FileEventType fileEventType) {
        this.fileObject = fileObject;
        this.fileEventId = fileEventId;
        this.fileEventType = fileEventType;
    }

    public FileEvent build() {
        var fileEvent = new FileEvent();

        fileEvent.setFileObject(fileObject);
        fileEvent.setFileEventId(fileEventId);
        fileEvent.setFileEventType(fileEventType);

        // OPTIONAL
        Optional.ofNullable(action).ifPresent(fileEvent::setAction);
        // OPTIONAL
        Optional.ofNullable(historyFlag).ifPresent(fileEvent::setHistoryFlag);
        // OPTIONAL
        Optional.ofNullable(cocHashValue).ifPresent(fileEvent::setCocHashValue);

        return fileEvent;
    }

    public void setAction(@NotNull Action action) {
        this.action = action;
    }

    public void setCocHashValue(@NotNull String cocHashValue) {
        this.cocHashValue = cocHashValue;
    }

    public void setHistoryFlag(@NotNull Boolean historyFlag) {
        this.historyFlag = historyFlag;
    }
}
