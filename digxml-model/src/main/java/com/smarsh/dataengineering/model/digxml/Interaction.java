package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interaction", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "timeFrame",
    "communicationObjectId",
    "parentCommunicationObjectId",
    "threadObjectId",
    "resourceLocation",
    "modality",
    "subject",
    "attributes",
    "participants",
    "fileEvents",
    "textEvents",
    "actionEvents",
    "policyEvents",
    "postattributes",
    "elementCocMasterHashValue",
    "nativeDataSize"
})
@XmlRootElement(name = "interaction", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
public class Interaction {

    @XmlElement(name = "time-frame", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected TimeFrame timeFrame;
    @XmlElement(name = "communication-object-id", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String communicationObjectId;
    @XmlElement(name = "parent-communication-object-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String parentCommunicationObjectId;
    @XmlElement(name = "thread-object-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String threadObjectId;
    @XmlElement(name = "resource-location", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String resourceLocation;
    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected Modality modality;
    @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected Subject subject;
    @XmlElement(name = "attributes", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected IndexableAttributes attributes;
    @XmlElement(name = "participants", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected Participants participants;
    @XmlElement(name = "file-events", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected FileEvents fileEvents;
    @XmlElement(name = "text-events", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected TextEvents textEvents;
    @XmlElement(name = "action-events", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected ActionEvents actionEvents;
    @XmlElement(name = "policy-events", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected PolicyEvents policyEvents;
    @XmlElement(name = "postattributes", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected IndexableAttributes postattributes;
    @XmlElement(name = "coc-master-hash-value", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected String elementCocMasterHashValue;
    @XmlElement(name = "native-data-size", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected Long nativeDataSize;
    @XmlAttribute(name = "interaction-event-id", required = true)
    protected String interactionEventId;
    @XmlAttribute(name = "historical-data-flag")
    protected Boolean historicalDataFlag;
    @XmlAttribute(name = "data-state")
    protected String dataState;
    @XmlAttribute(name = "coc-hash-algorithm")
    protected String cocHashAlgorithm;
    @XmlAttribute(name = "coc-hash-encoding")
    protected String cocHashEncoding;
    @XmlAttribute(name = "coc-hash-type")
    protected String cocHashType;
    @XmlAttribute(name = "coc-master-hash-value")
    protected String attributeCocMasterHashValue;
    @XmlAttribute(name = "coc-hash-value")
    protected String cocHashValue;
    @XmlAttribute(name = "coc-version")
    protected String cocVersion;

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame value) {
        this.timeFrame = value;
    }

    public String getCommunicationObjectId() {
        return communicationObjectId;
    }

    public void setCommunicationObjectId(String value) {
        this.communicationObjectId = value;
    }

    public String getParentCommunicationObjectId() {
        return parentCommunicationObjectId;
    }

    public void setParentCommunicationObjectId(String value) {
        this.parentCommunicationObjectId = value;
    }

    public String getThreadObjectId() {
        return threadObjectId;
    }

    public void setThreadObjectId(String value) {
        this.threadObjectId = value;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String value) {
        this.resourceLocation = value;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality value) {
        this.modality = value;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject value) {
        this.subject = value;
    }

    public IndexableAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(IndexableAttributes value) {
        this.attributes = value;
    }

    public Participants getParticipants() {
        return participants;
    }

    public void setParticipants(Participants value) {
        this.participants = value;
    }

    public FileEvents getFileEvents() {
        return fileEvents;
    }

    public void setFileEvents(FileEvents value) {
        this.fileEvents = value;
    }

    public TextEvents getTextEvents() {
        return textEvents;
    }

    public void setTextEvents(TextEvents value) {
        this.textEvents = value;
    }

    public ActionEvents getActionEvents() {
        return actionEvents;
    }

    public void setActionEvents(ActionEvents value) {
        this.actionEvents = value;
    }

    public PolicyEvents getPolicyEvents() {
        return policyEvents;
    }

    public void setPolicyEvents(PolicyEvents value) {
        this.policyEvents = value;
    }

    public IndexableAttributes getPostattributes() {
        return postattributes;
    }

    public void setPostattributes(IndexableAttributes value) {
        this.postattributes = value;
    }

    public String getElementCocMasterHashValue() {
        return elementCocMasterHashValue;
    }

    public void setElementCocMasterHashValue(String value) {
        this.elementCocMasterHashValue = value;
    }

    public Long getNativeDataSize() {
        return nativeDataSize;
    }

    public void setNativeDataSize(Long value) {
        this.nativeDataSize = value;
    }

    public String getInteractionEventId() {
        return interactionEventId;
    }

    public void setInteractionEventId(String value) {
        this.interactionEventId = value;
    }

    public boolean isHistoricalDataFlag() {
        if (historicalDataFlag == null) {
            return false;
        } else {
            return historicalDataFlag;
        }
    }

    public void setHistoricalDataFlag(Boolean value) {
        this.historicalDataFlag = value;
    }

    public String getDataState() {
        if (dataState == null) {
            return "valid";
        } else {
            return dataState;
        }
    }

    public void setDataState(String value) {
        this.dataState = value;
    }

    public String getCocHashAlgorithm() {
        if (cocHashAlgorithm == null) {
            return "sha1";
        } else {
            return cocHashAlgorithm;
        }
    }

    public void setCocHashAlgorithm(String value) {
        this.cocHashAlgorithm = value;
    }

    public String getCocHashEncoding() {
        if (cocHashEncoding == null) {
            return "hex";
        } else {
            return cocHashEncoding;
        }
    }

    public void setCocHashEncoding(String value) {
        this.cocHashEncoding = value;
    }

    public String getCocHashType() {
        if (cocHashType == null) {
            return "full";
        } else {
            return cocHashType;
        }
    }

    public void setCocHashType(String value) {
        this.cocHashType = value;
    }

    public String getAttributeCocMasterHashValue() {
        return attributeCocMasterHashValue;
    }

    public void setAttributeCocMasterHashValue(String value) {
        this.attributeCocMasterHashValue = value;
    }

    public String getCocHashValue() {
        return cocHashValue;
    }

    public void setCocHashValue(String value) {
        this.cocHashValue = value;
    }

    public String getCocVersion() {
        return cocVersion;
    }

    public void setCocVersion(String value) {
        this.cocVersion = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "actionEvent"
    })
    public static class ActionEvents {

        @XmlElement(name = "action-event", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
        protected List<ActionEvent> actionEvent;

        public List<ActionEvent> getActionEvent() {
            if (actionEvent == null) {
                actionEvent = new ArrayList<ActionEvent>();
            }
            return this.actionEvent;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fileEvent"
    })
    public static class FileEvents {

        @XmlElement(name = "file-event", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
        protected List<FileEvent> fileEvent;

        public List<FileEvent> getFileEvent() {
            if (fileEvent == null) {
                fileEvent = new ArrayList<FileEvent>();
            }
            return this.fileEvent;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "participants", propOrder = {
        "participant"
    })
    public static class Participants {

        @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
        protected List<Participant> participant;

        public List<Participant> getParticipant() {
            if (participant == null) {
                participant = new ArrayList<>();
            }
            return this.participant;
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "participant", propOrder = {
            "networkInfo",
            "userInfo",
            "attributes"
        })
        public static class Participant {

            @XmlElement(name = "network-info", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
            protected NetworkEndpoint networkInfo;
            @XmlElement(name = "user-info", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", required = true)
            protected UserInfo userInfo;
            @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
            protected GeneralAttributes attributes;
            @XmlAttribute(name = "participant-id", required = true)
            protected String participantId;
            @XmlAttribute(name = "participant-role")
            protected String participantRole;

            public NetworkEndpoint getNetworkInfo() {
                return networkInfo;
            }

            public void setNetworkInfo(NetworkEndpoint value) {
                this.networkInfo = value;
            }

            public UserInfo getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfo value) {
                this.userInfo = value;
            }

            public GeneralAttributes getAttributes() {
                return attributes;
            }

            public void setAttributes(GeneralAttributes value) {
                this.attributes = value;
            }

            public String getParticipantId() {
                return participantId;
            }

            public void setParticipantId(String value) {
                this.participantId = value;
            }

            public String getParticipantRole() {
                if (participantRole == null) {
                    return "participant";
                } else {
                    return participantRole;
                }
            }

            public void setParticipantRole(String value) {
                this.participantRole = value;
            }

        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "policyEvent"
    })
    public static class PolicyEvents {

        @XmlElement(name = "policy-event", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
        protected List<PolicyEvent> policyEvent;

        public List<PolicyEvent> getPolicyEvent() {
            if (policyEvent == null) {
                policyEvent = new ArrayList<PolicyEvent>();
            }
            return this.policyEvent;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "textEvent"
    })
    public static class TextEvents {

        @XmlElement(name = "text-event", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
        protected List<TextEvent> textEvent;

        public List<TextEvent> getTextEvent() {
            if (textEvent == null) {
                textEvent = new ArrayList<TextEvent>();
            }
            return this.textEvent;
        }

    }
}
