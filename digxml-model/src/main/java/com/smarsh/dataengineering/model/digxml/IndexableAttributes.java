package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indexableAttributes", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", propOrder = {
    "attribute"
})
@XmlRootElement(name = "indexableAttributes", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
public class IndexableAttributes {

    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected List<Attribute> attribute;

    public List<Attribute> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<>();
        }
        return this.attribute;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "attribute", propOrder = {
        "value"
    })
    public static class Attribute {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "content-type")
        protected MimeType contentType;
        @XmlAttribute(name = "indexable", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected Boolean indexable;
        @XmlAttribute(name = "user-visible")
        protected UserVisibility userVisible;
        @XmlAttribute(name = "classification")
        protected String classification;
        @XmlAttribute(name = "system-flag")
        protected Boolean systemFlag;

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

        public MimeType getContentType() {
            if (contentType == null) {
                return MimeType.TEXT_PLAIN;
            } else {
                return contentType;
            }
        }

        public void setContentType(MimeType value) {
            this.contentType = value;
        }

        public boolean isIndexable() {
            if (indexable == null) {
                return true;
            } else {
                return indexable;
            }
        }

        public void setIndexable(Boolean value) {
            this.indexable = value;
        }

        public UserVisibility getUserVisible() {
            if (userVisible == null) {
                return UserVisibility.ALWAYS_VISIBLE;
            } else {
                return userVisible;
            }
        }

        public void setUserVisible(UserVisibility value) {
            this.userVisible = value;
        }

        public String getClassification() {
            if (classification == null) {
                return "";
            } else {
                return classification;
            }
        }

        public void setClassification(String value) {
            this.classification = value;
        }

        public boolean isSystemFlag() {
            if (systemFlag == null) {
                return false;
            } else {
                return systemFlag;
            }
        }

        public void setSystemFlag(Boolean value) {
            this.systemFlag = value;
        }
    }

}
