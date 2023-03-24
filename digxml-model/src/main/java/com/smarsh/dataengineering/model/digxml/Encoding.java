package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name = "Encoding")
@XmlEnum
public enum Encoding {

    @XmlEnumValue("base64")
    BASE_64("base64"),
    @XmlEnumValue("text")
    TEXT("text");
    private final String value;

    Encoding(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Encoding fromValue(String v) {
        for (Encoding c: Encoding.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
