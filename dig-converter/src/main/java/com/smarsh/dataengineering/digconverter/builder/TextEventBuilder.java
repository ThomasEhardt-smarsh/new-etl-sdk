package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Action;
import com.smarsh.dataengineering.model.TextEvent;
import com.smarsh.dataengineering.model.TextObject;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TextEventBuilder implements AbstractBuilder<TextEventBuilder, TextEvent> {

    @NotNull
    private TextObject textObject;
    @NotNull
    private String textEventId;


    @Nullable
    private Action action;
    @Nullable
    private String operation;
    @Nullable
    private Boolean historyFlag;
    @Nullable
    private String cocHashValue;

    public TextEventBuilder(@NotNull TextObject textObject, @NotNull String textEventId) {
        this.textObject = textObject;
        this.textEventId = textEventId;
    }

    @Override
    public TextEvent build() {
        var textEvent = new TextEvent();
        textEvent.setTextObject(textObject);
        textEvent.setTextEventId(textEventId);

        Optional.ofNullable(action).ifPresent(textEvent::setAction);
        Optional.ofNullable(operation).ifPresent(textEvent::setOperation);
        Optional.ofNullable(historyFlag).ifPresent(textEvent::setHistoryFlag);
        Optional.ofNullable(cocHashValue).ifPresent(textEvent::setCocHashValue);

        return textEvent;
    }

    public void setAction(@NotNull Action action) {
        this.action = action;
    }

    public void setOperation(@NotNull String operation) {
        this.operation = operation;
    }

    public void setHistoryFlag(@NotNull Boolean historyFlag) {
        this.historyFlag = historyFlag;
    }

    public void setCocHashValue(@NotNull String cocHashValue) {
        this.cocHashValue = cocHashValue;
    }
}
