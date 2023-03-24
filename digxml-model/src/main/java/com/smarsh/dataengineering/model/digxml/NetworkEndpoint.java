package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetworkEndpoint", propOrder = {
    "network",
    "endpointId",
    "displayName",
    "groups"
})
public class NetworkEndpoint {

    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String network;
    @XmlElement(name = "endpoint-id", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String endpointId;
    @XmlElement(name = "display-name", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String displayName;
    @XmlElement(name = "groups", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected Groups groups;
    @XmlAttribute(name = "endpoint-id-type")
    protected String endpointIdType;

    /**
     * Network or product
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetwork(String value) {
        this.network = value;
    }

    /**
     * Gets the value of the endpointId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndpointId() {
        return endpointId;
    }

    /**
     * Sets the value of the endpointId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndpointId(String value) {
        this.endpointId = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the groups property.
     * 
     * @return
     *     possible object is
     *     {@link Groups }
     *     
     */
    public Groups getGroups() {
        return groups;
    }

    /**
     * Sets the value of the groups property.
     * 
     * @param value
     *     allowed object is
     *     {@link Groups }
     *     
     */
    public void setGroups(Groups value) {
        this.groups = value;
    }

    /**
     * Gets the value of the endpointIdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndpointIdType() {
        if (endpointIdType == null) {
            return "login";
        } else {
            return endpointIdType;
        }
    }

    /**
     * Sets the value of the endpointIdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndpointIdType(String value) {
        this.endpointIdType = value;
    }

}
