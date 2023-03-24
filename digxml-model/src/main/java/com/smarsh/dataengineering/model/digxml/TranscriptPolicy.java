package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transcript-policy", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0", propOrder = {
    "retention",
    "disposition"
})
public class TranscriptPolicy {

    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0")
    protected Retention retention;
    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0")
    protected String disposition;

    public Retention getRetention() {
        return retention;
    }

    public void setRetention(Retention value) {
        this.retention = value;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String value) {
        this.disposition = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "policy"
    })
    public static class Retention {

        @XmlElement(namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0")
        protected List<Policy> policy;

        public List<Policy> getPolicy() {
            if (policy == null) {
                policy = new ArrayList<Policy>();
            }
            return this.policy;
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Policy {

            @XmlAttribute(name = "action", required = true)
            protected String action;
            @XmlAttribute(name = "decription")
            @XmlSchemaType(name = "anySimpleType")
            protected String decription;
            @XmlAttribute(name = "id")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String id;

            public String getAction() {
                return action;
            }

            public void setAction(String value) {
                this.action = value;
            }

            public String getDecription() {
                return decription;
            }

            public void setDecription(String value) {
                this.decription = value;
            }

            public String getId() {
                return id;
            }

            public void setId(String value) {
                this.id = value;
            }

        }

    }

}
