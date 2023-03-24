package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transcript", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "interaction",
    "policy"
})
@XmlRootElement(name = "transcript", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
public class Transcript {

    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
    protected Interaction interaction;
    @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0")
    protected TranscriptPolicy policy;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "source-endpoint-id", required = true)
    protected String sourceEndpointId;
    @XmlAttribute(name = "source-endpoint-version")
    protected String sourceEndpointVersion;
    @XmlAttribute(name = "time-stamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction value) {
        this.interaction = value;
    }

    public TranscriptPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(TranscriptPolicy value) {
        this.policy = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getSourceEndpointId() {
        return sourceEndpointId;
    }

    public void setSourceEndpointId(String value) {
        this.sourceEndpointId = value;
    }

    public String getSourceEndpointVersion() {
        return sourceEndpointVersion;
    }

    public void setSourceEndpointVersion(String value) {
        this.sourceEndpointVersion = value;
    }

    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

}
