package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameType", propOrder = {
    "first",
    "middle",
    "last",
    "initials",
    "displayName"
})
@XmlRootElement(name = "name", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
public class NameType {

    @XmlElement(name = "first", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String first;
    @XmlElement(name = "middle", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String middle;
    @XmlElement(name = "last", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String last;
    @XmlElement(name = "initials", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String initials;
    @XmlElement(name = "display-name", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String displayName;

    public String getFirst() {
        return first;
    }

    public void setFirst(String value) {
        this.first = value;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String value) {
        this.middle = value;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String value) {
        this.last = value;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String value) {
        this.initials = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }
}
