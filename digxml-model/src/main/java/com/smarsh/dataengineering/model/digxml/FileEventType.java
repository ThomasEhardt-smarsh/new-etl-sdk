package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "FileEventType")
@XmlEnum
public enum FileEventType {

    @XmlEnumValue("inline")
    INLINE("inline"),
    @XmlEnumValue("attachment")
    ATTACHMENT("attachment"),
    @XmlEnumValue("raw")
    RAW("raw"),
    @XmlEnumValue("body")
    BODY("body");
    private final String value;

    FileEventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FileEventType fromValue(String v) {
        for (FileEventType c: FileEventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
