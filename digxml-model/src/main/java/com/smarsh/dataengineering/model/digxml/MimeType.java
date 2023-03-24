package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name = "MimeType")
@XmlEnum
public enum MimeType {

    @XmlEnumValue("text/plain")
    TEXT_PLAIN("text/plain"),
    @XmlEnumValue("text/html")
    TEXT_HTML("text/html"),
    @XmlEnumValue("text/xml")
    TEXT_XML("text/xml"),
    @XmlEnumValue("text/rtf")
    TEXT_RTF("text/rtf");
    private final String value;

    private MimeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MimeType fromValue(String v) {
        for (MimeType c: MimeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
