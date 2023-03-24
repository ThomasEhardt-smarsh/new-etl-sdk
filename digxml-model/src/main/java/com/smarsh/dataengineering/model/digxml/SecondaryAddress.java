package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecondaryAddress", propOrder = {
    "value"
})
public class SecondaryAddress {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "address-type", required = true)
    protected String addressType;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String value) {
        this.addressType = value;
    }

}
