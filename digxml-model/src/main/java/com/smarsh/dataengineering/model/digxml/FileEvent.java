package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileEvent", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "fileObject"
})
public class FileEvent {

    @XmlElement(name = "file-object", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected FileObject fileObject;
    @XmlAttribute(name = "file-event-id", required = true)
    protected String fileEventId;
    @XmlAttribute(name = "file-event-type", required = true)
    protected FileEventType fileEventType;
    @XmlAttribute(name = "action")
    protected Action action;
    @XmlAttribute(name = "history-flag")
    protected Boolean historyFlag;
    @XmlAttribute(name = "coc-hash-value")
    protected String cocHashValue;

    public FileObject getFileObject() {
        return fileObject;
    }

    public void setFileObject(FileObject value) {
        this.fileObject = value;
    }

    public String getFileEventId() {
        return fileEventId;
    }

    public void setFileEventId(String value) {
        this.fileEventId = value;
    }

    public FileEventType getFileEventType() {
        return fileEventType;
    }

    public void setFileEventType(FileEventType value) {
        this.fileEventType = value;
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
