package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileObject", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "fileObjectId",
    "parentFileObjectId",
    "eventTime",
    "participantId",
    "fileObjectUrl",
    "duration",
    "fileContent",
    "attributes",
    "lexiconHits"
})
public class FileObject {

    @XmlElement(name = "file-object-id", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String fileObjectId;
    @XmlElement(name = "parent-file-object-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String parentFileObjectId;
    @XmlElement(name = "event-time", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TimeStamp eventTime;
    @XmlElement(name = "participant-id", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String participantId;
    @XmlElement(name = "file-object-url", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    @XmlSchemaType(name = "anyURI")
    protected String fileObjectUrl;
    protected Long duration;
    @XmlElement(name = "file-content", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected FileContent fileContent;
    @XmlElement(name = "attributes", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected IndexableAttributes attributes;
    @XmlElement(name = "lexicon-hits", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected LexiconHits lexiconHits;

    public String getFileObjectId() {
        return fileObjectId;
    }

    public void setFileObjectId(String value) {
        this.fileObjectId = value;
    }

    public String getParentFileObjectId() {
        return parentFileObjectId;
    }

    public void setParentFileObjectId(String value) {
        this.parentFileObjectId = value;
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

    public String getFileObjectUrl() {
        return fileObjectUrl;
    }

    public void setFileObjectUrl(String value) {
        this.fileObjectUrl = value;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long value) {
        this.duration = value;
    }

    public FileContent getFileContent() {
        return fileContent;
    }

    public void setFileContent(FileContent value) {
        this.fileContent = value;
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
        "value"
    })
    public static class FileContent {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "file-path")
        @XmlSchemaType(name = "anyURI")
        protected String filePath;
        @XmlAttribute(name = "name")
        protected String name;
        @XmlAttribute(name = "content-type")
        protected String contentType;
        @XmlAttribute(name = "encoding")
        protected Encoding encoding;
        @XmlAttribute(name = "indexable", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected Boolean indexable;
        @XmlAttribute(name = "user-visible")
        protected UserVisibility userVisible;

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String value) {
            this.filePath = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getContentType() {
            if (contentType == null) {
                return "text/plain";
            } else {
                return contentType;
            }
        }

        public void setContentType(String value) {
            this.contentType = value;
        }

        public Encoding getEncoding() {
            if (encoding == null) {
                return Encoding.BASE_64;
            } else {
                return encoding;
            }
        }

        public void setEncoding(Encoding value) {
            this.encoding = value;
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
}
