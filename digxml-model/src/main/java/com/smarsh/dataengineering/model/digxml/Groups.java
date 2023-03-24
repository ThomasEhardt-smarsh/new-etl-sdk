package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groups", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0", propOrder = {
    "group"
})
@XmlRootElement(name = "groups", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
public class Groups {

    @XmlElement(name = "group", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected List<String> group;

    public List<String> getGroup() {
        if (group == null) {
            group = new ArrayList<>();
        }
        return this.group;
    }
}
