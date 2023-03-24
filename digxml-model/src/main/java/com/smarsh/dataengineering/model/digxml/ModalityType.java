package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name = "ModalityType")
@XmlEnum
public enum ModalityType {

    @XmlEnumValue("public")
    PUBLIC("public"),
    @XmlEnumValue("private")
    PRIVATE("private"),
    @XmlEnumValue("federated")
    FEDERATED("federated");
    private final String value;

    ModalityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModalityType fromValue(String v) {
        for (ModalityType c: ModalityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
