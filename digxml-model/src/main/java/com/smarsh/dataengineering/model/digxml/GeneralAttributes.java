package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneralAttributes", propOrder = {
    "attribute"
})
public class GeneralAttributes {

    @XmlElement(required = true)
    protected List<Attribute> attribute;

    public List<Attribute> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<Attribute>();
        }
        return this.attribute;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Attribute {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "content-type")
        protected String contentType;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getContentType() {
            if (contentType == null) {
                return "text/plain";
            } else {
                return contentType;
            }
        }

        public void setContentType(String value) {
            this.contentType = value;
        }
    }
}
