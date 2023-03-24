package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name = "Action")
@XmlEnum
public enum Action {

    @XmlEnumValue("create")
    CREATE("create"),
    @XmlEnumValue("update")
    UPDATE("update"),
    @XmlEnumValue("delete")
    DELETE("delete");
    private final String value;

    private Action(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Action fromValue(String v) {
        for (Action c: Action.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
