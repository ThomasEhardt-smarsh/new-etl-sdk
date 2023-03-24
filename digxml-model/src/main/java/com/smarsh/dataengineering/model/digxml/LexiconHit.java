package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.math.BigInteger;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LexiconHit", propOrder = {
    "value"
})
public class LexiconHit {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "match-type", required = true)
    protected String matchType;
    @XmlAttribute(name = "near-distance")
    protected BigInteger nearDistance;
    @XmlAttribute(name = "case-sensitive")
    protected Boolean caseSensitive;
    @XmlAttribute(name = "plain-text")
    protected Boolean plainText;
    @XmlAttribute(name = "offset")
    protected Long offset;
    @XmlAttribute(name = "length")
    protected BigInteger length;
    @XmlAttribute(name = "origin")
    protected String origin;
    @XmlAttribute(name = "category")
    protected String category;
    @XmlAttribute(name = "policy")
    protected String policy;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String value) {
        this.matchType = value;
    }

    public BigInteger getNearDistance() {
        return nearDistance;
    }

    public void setNearDistance(BigInteger value) {
        this.nearDistance = value;
    }

    public boolean isCaseSensitive() {
        if (caseSensitive == null) {
            return false;
        } else {
            return caseSensitive;
        }
    }

    public void setCaseSensitive(Boolean value) {
        this.caseSensitive = value;
    }

    public boolean isPlainText() {
        if (plainText == null) {
            return true;
        } else {
            return plainText;
        }
    }

    public void setPlainText(Boolean value) {
        this.plainText = value;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long value) {
        this.offset = value;
    }

    public BigInteger getLength() {
        return length;
    }

    public void setLength(BigInteger value) {
        this.length = value;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String value) {
        this.origin = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String value) {
        this.category = value;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String value) {
        this.policy = value;
    }
}
