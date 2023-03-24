package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyEvent", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "policyEventType",
    "policyEventSubType",
    "policyEventCategory",
    "eventTime",
    "triggeringParticipantIds",
    "policy",
    "actionEventIds",
    "triggeringTextEventIds",
    "triggeringFileEventIds",
    "textContent",
    "attributes"
})
public class PolicyEvent {

    @XmlElement(name = "policy-event-type", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String policyEventType;
    @XmlElement(name = "policy-event-sub-type", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String policyEventSubType;
    @XmlElement(name = "policy-event-category", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String policyEventCategory;
    @XmlElement(name = "event-time", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TimeStamp eventTime;
    @XmlElement(name = "triggering-participant-ids", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TriggeringParticipantIds triggeringParticipantIds;
    @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String policy;
    @XmlElement(name = "action-event-ids", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected ActionEventIds actionEventIds;
    @XmlElement(name = "triggering-text-event-ids", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TriggeringTextEventIds triggeringTextEventIds;
    @XmlElement(name = "triggering-file-event-ids", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TriggeringFileEventIds triggeringFileEventIds;
    @XmlElement(name = "text-content", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TextContent textContent;
    @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected IndexableAttributes attributes;
    @XmlAttribute(name = "policy-event-id", required = true)
    protected String policyEventId;
    @XmlAttribute(name = "history-flag")
    protected Boolean historyFlag;
    @XmlAttribute(name = "coc-hash-value")
    protected String cocHashValue;

    public String getPolicyEventType() {
        return policyEventType;
    }

    public void setPolicyEventType(String value) {
        this.policyEventType = value;
    }

    public String getPolicyEventSubType() {
        return policyEventSubType;
    }

    public void setPolicyEventSubType(String value) {
        this.policyEventSubType = value;
    }

    public String getPolicyEventCategory() {
        return policyEventCategory;
    }

    public void setPolicyEventCategory(String value) {
        this.policyEventCategory = value;
    }

    public TimeStamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(TimeStamp value) {
        this.eventTime = value;
    }

    public TriggeringParticipantIds getTriggeringParticipantIds() {
        return triggeringParticipantIds;
    }

    public void setTriggeringParticipantIds(TriggeringParticipantIds value) {
        this.triggeringParticipantIds = value;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String value) {
        this.policy = value;
    }

    public ActionEventIds getActionEventIds() {
        return actionEventIds;
    }

    public void setActionEventIds(ActionEventIds value) {
        this.actionEventIds = value;
    }

    public TriggeringTextEventIds getTriggeringTextEventIds() {
        return triggeringTextEventIds;
    }

    public void setTriggeringTextEventIds(TriggeringTextEventIds value) {
        this.triggeringTextEventIds = value;
    }

    public TriggeringFileEventIds getTriggeringFileEventIds() {
        return triggeringFileEventIds;
    }

    public void setTriggeringFileEventIds(TriggeringFileEventIds value) {
        this.triggeringFileEventIds = value;
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

    public String getPolicyEventId() {
        return policyEventId;
    }

    public void setPolicyEventId(String value) {
        this.policyEventId = value;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "actionEventId"
    })
    public static class ActionEventIds {

        @XmlElement(name = "action-event-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
        protected List<String> actionEventId;

        public List<String> getActionEventId() {
            if (actionEventId == null) {
                actionEventId = new ArrayList<String>();
            }
            return this.actionEventId;
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

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fileEventId"
    })
    public static class TriggeringFileEventIds {

        @XmlElement(name = "file-event-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
        protected List<String> fileEventId;

        /**
         * Gets the value of the fileEventId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the fileEventId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFileEventId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getFileEventId() {
            if (fileEventId == null) {
                fileEventId = new ArrayList<String>();
            }
            return this.fileEventId;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "triggeringParticipantId"
    })
    public static class TriggeringParticipantIds {

        @XmlElement(name = "triggering-participant-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
        protected List<String> triggeringParticipantId;

        /**
         * Gets the value of the triggeringParticipantId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the triggeringParticipantId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTriggeringParticipantId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTriggeringParticipantId() {
            if (triggeringParticipantId == null) {
                triggeringParticipantId = new ArrayList<String>();
            }
            return this.triggeringParticipantId;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "textEventId"
    })
    public static class TriggeringTextEventIds {

        @XmlElement(name = "text-event-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
        protected List<String> textEventId;

        /**
         * Gets the value of the textEventId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the textEventId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTextEventId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTextEventId() {
            if (textEventId == null) {
                textEventId = new ArrayList<String>();
            }
            return this.textEventId;
        }

    }
}
