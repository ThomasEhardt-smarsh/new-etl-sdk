package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "time-frame", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0", propOrder = {
    "startTime",
    "endTime"
})
@XmlRootElement(name = "time-frame", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
public class TimeFrame {

    @XmlElement(name = "start-time", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected TimeStamp startTime;
    @XmlElement(name = "end-time", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected TimeStamp endTime;

    public TimeStamp getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeStamp value) {
        this.startTime = value;
    }

    public TimeStamp getEndTime() {
        return endTime;
    }

    public void setEndTime(TimeStamp value) {
        this.endTime = value;
    }

}
