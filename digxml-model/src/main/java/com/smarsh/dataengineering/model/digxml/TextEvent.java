package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextEvent", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "textObject"
})
public class TextEvent {

    @XmlElement(name = "text-object", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TextObject textObject;
    @XmlAttribute(name = "text-event-id", required = true)
    protected String textEventId;
    @XmlAttribute(name = "action")
    protected Action action;
    @XmlAttribute(name = "operation")
    protected String operation;
    @XmlAttribute(name = "history-flag")
    protected Boolean historyFlag;
    @XmlAttribute(name = "coc-hash-value")
    protected String cocHashValue;

    public TextObject getTextObject() {
        return textObject;
    }

    public void setTextObject(TextObject value) {
        this.textObject = value;
    }

    public String getTextEventId() {
        return textEventId;
    }

    public void setTextEventId(String value) {
        this.textEventId = value;
    }

    public Action getAction() {
        if (action == null) {
            return Action.CREATE;
        } else {
            return action;
        }
    }

    public void setAction(Action value) {
        this.action = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String value) {
        this.operation = value;
    }

    public boolean isHistoryFlag() {
        if (historyFlag == null) {
            return false;
        } else {
            return historyFlag;
        }
    }

    public void setHistoryFlag(Boolean value) {
        this.historyFlag = value;
    }

    public String getCocHashValue() {
        return cocHashValue;
    }

    public void setCocHashValue(String value) {
        this.cocHashValue = value;
    }
}
