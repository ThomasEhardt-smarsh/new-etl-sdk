package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "channel",
    "network"
})
@XmlRootElement(name = "modality")
public class Modality {

    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String channel;
    @XmlElement(required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String network;
    @XmlAttribute(name = "classification")
    protected ModalityClass classification;
    @XmlAttribute(name = "type")
    protected ModalityType type;
    @XmlAttribute(name = "vendor")
    protected String vendor;
    @XmlAttribute(name = "description")
    protected String description;

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

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
     * Gets the value of the classification property.
     * 
     * @return
     *     possible object is
     *     {@link ModalityClass }
     *     
     */
    public ModalityClass getClassification() {
        return classification;
    }

    /**
     * Sets the value of the classification property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalityClass }
     *     
     */
    public void setClassification(ModalityClass value) {
        this.classification = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ModalityType }
     *     
     */
    public ModalityType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalityType }
     *     
     */
    public void setType(ModalityType value) {
        this.type = value;
    }

    /**
     * Gets the value of the vendor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Sets the value of the vendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendor(String value) {
        this.vendor = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
