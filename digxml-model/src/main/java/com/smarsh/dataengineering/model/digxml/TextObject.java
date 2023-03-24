package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextObject", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "textObjectId",
    "parentTextObjectId",
    "systemFlag",
    "textObjectStyle",
    "textObjectType",
    "textObjectSubType",
    "eventTime",
    "participantId",
    "textObjectUrl",
    "fileEventIds",
    "linkCommunicationObjectId",
    "textContent",
    "attributes",
    "lexiconHits"
})
public class TextObject {

    @XmlElement(name = "text-object-id", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String textObjectId;
    @XmlElement(name = "parent-text-object-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String parentTextObjectId;
    @XmlElement(name = "system-flag", defaultValue = "false", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected Boolean systemFlag;
    @XmlElement(name = "text-object-style", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    @XmlSchemaType(name = "string")
    protected TextEventType textObjectStyle;
    @XmlElement(name = "text-object-type", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String textObjectType;
    @XmlElement(name = "text-object-sub-type", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String textObjectSubType;
    @XmlElement(name = "event-time", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TimeStamp eventTime;
    @XmlElement(name = "participant-id", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String participantId;
    @XmlElement(name = "text-object-url", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    @XmlSchemaType(name = "anyURI")
    protected String textObjectUrl;
    @XmlElement(name = "file-event-ids", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected FileEventIds fileEventIds;
    @XmlElement(name = "link-communication-object-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String linkCommunicationObjectId;
    @XmlElement(name = "text-content", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TextContent textContent;
    @XmlElement(name = "attributes", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected IndexableAttributes attributes;
    @XmlElement(name = "lexicon-hits", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected LexiconHits lexiconHits;

    public String getTextObjectId() {
        return textObjectId;
    }

    public void setTextObjectId(String value) {
        this.textObjectId = value;
    }

    public String getParentTextObjectId() {
        return parentTextObjectId;
    }

    public void setParentTextObjectId(String value) {
        this.parentTextObjectId = value;
    }

    public Boolean isSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(Boolean value) {
        this.systemFlag = value;
    }

    public TextEventType getTextObjectStyle() {
        return textObjectStyle;
    }

    public void setTextObjectStyle(TextEventType value) {
        this.textObjectStyle = value;
    }

    public String getTextObjectType() {
        return textObjectType;
    }

    public void setTextObjectType(String value) {
        this.textObjectType = value;
    }

    public String getTextObjectSubType() {
        return textObjectSubType;
    }

    public void setTextObjectSubType(String value) {
        this.textObjectSubType = value;
    }

    public TimeStamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(TimeStamp value) {
        this.eventTime = value;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String value) {
        this.participantId = value;
    }

    public String getTextObjectUrl() {
        return textObjectUrl;
    }

    public void setTextObjectUrl(String value) {
        this.textObjectUrl = value;
    }

    public FileEventIds getFileEventIds() {
        return fileEventIds;
    }

    public void setFileEventIds(FileEventIds value) {
        this.fileEventIds = value;
    }

    public String getLinkCommunicationObjectId() {
        return linkCommunicationObjectId;
    }

    public void setLinkCommunicationObjectId(String value) {
        this.linkCommunicationObjectId = value;
    }

    public TextContent getTextContent() {
        return textContent;
    }

    public void setTextContent(TextContent value) {
        this.textContent = value;
    }

    public IndexableAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(IndexableAttributes value) {
        this.attributes = value;
    }

    public LexiconHits getLexiconHits() {
        return lexiconHits;
    }

    public void setLexiconHits(LexiconHits value) {
        this.lexiconHits = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fileEventId"
    })
    public static class FileEventIds {

        @XmlElement(name = "file-event-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
        protected List<String> fileEventId;

        public List<String> getFileEventId() {
            if (fileEventId == null) {
                fileEventId = new ArrayList<String>();
            }
            return this.fileEventId;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "lexiconHit"
    })
    public static class LexiconHits {

        @XmlElement(name = "lexicon-hit", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
        protected List<LexiconHit> lexiconHit;

        public List<LexiconHit> getLexiconHit() {
            if (lexiconHit == null) {
                lexiconHit = new ArrayList<LexiconHit>();
            }
            return this.lexiconHit;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class TextContent {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "content-type")
        protected MimeType contentType;
        @XmlAttribute(name = "indexable", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected Boolean indexable;
        @XmlAttribute(name = "user-visible")
        protected UserVisibility userVisible;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public MimeType getContentType() {
            if (contentType == null) {
                return MimeType.TEXT_PLAIN;
            } else {
                return contentType;
            }
        }

        public void setContentType(MimeType value) {
            this.contentType = value;
        }

        public boolean isIndexable() {
            if (indexable == null) {
                return true;
            } else {
                return indexable;
            }
        }

        public void setIndexable(Boolean value) {
            this.indexable = value;
        }

        public UserVisibility getUserVisible() {
            if (userVisible == null) {
                return UserVisibility.ALWAYS_VISIBLE;
            } else {
                return userVisible;
            }
        }

        public void setUserVisible(UserVisibility value) {
            this.userVisible = value;
        }
    }
}
