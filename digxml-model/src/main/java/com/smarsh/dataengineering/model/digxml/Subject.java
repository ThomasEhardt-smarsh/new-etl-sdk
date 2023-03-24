package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "subject")
public class Subject {

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
