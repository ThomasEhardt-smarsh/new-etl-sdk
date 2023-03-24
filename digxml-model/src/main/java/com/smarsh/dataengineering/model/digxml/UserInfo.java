package com.smarsh.dataengineering.model.digxml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInfo", propOrder = {
    "userId",
    "userType",
    "name",
    "emailAddress",
    "affiliation",
    "geoLocation",
    "phoneNumbers",
    "secondaryAddresses",
    "groups",
    "image",
    "modificationTime"
})
@XmlRootElement(name = "user-info", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0")
public class UserInfo {

    @XmlElement(name = "user-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String userId;
    @XmlElement(name = "user-type", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String userType;
    @XmlElement(name = "name", required = true, namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected NameType name;
    @XmlElement(name = "email-address", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected String emailAddress;
    @XmlElement(name = "affiliation", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected Affiliation affiliation;
    @XmlElement(name = "geo-location", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected GeoLocation geoLocation;
    @XmlElement(name = "phone-numbers", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected PhoneNumbers phoneNumbers;
    @XmlElement(name = "secondary-addresses", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected SecondaryAddresses secondaryAddresses;
    @XmlElement(name = "groups", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected Groups groups;
    @XmlElement(name = "image", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected Image image;
    @XmlElement(name = "modification-time", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
    protected TimeStamp modificationTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String value) {
        this.userType = value;
    }

    public NameType getName() {
        return name;
    }

    public void setName(NameType value) {
        this.name = value;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation value) {
        this.affiliation = value;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation value) {
        this.geoLocation = value;
    }

    public PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(PhoneNumbers value) {
        this.phoneNumbers = value;
    }

    public SecondaryAddresses getSecondaryAddresses() {
        return secondaryAddresses;
    }

    public void setSecondaryAddresses(SecondaryAddresses value) {
        this.secondaryAddresses = value;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups value) {
        this.groups = value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image value) {
        this.image = value;
    }

    public TimeStamp getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(TimeStamp value) {
        this.modificationTime = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0", propOrder = {
        "employeeId",
        "title",
        "department",
        "division",
        "company",
        "building"
    })
    public static class Affiliation {

        @XmlElement(name = "employee-id", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String employeeId;
        @XmlElement(name = "title", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String title;
        @XmlElement(name = "department", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String department;
        @XmlElement(name = "division", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String division;
        @XmlElement(name = "company", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String company;
        @XmlElement(name = "building", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String building;

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String value) {
            this.employeeId = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String value) {
            this.department = value;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String value) {
            this.division = value;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String value) {
            this.company = value;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String value) {
            this.building = value;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0", propOrder = {
        "city",
        "state",
        "country"
    })
    public static class GeoLocation {

        @XmlElement(name = "city", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String city;
        @XmlElement(name = "state", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String state;
        @XmlElement(name = "country", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String country;

        /**
         * Gets the value of the city property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCity() {
            return city;
        }

        /**
         * Sets the value of the city property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Gets the value of the country property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountry(String value) {
            this.country = value;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Image {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "type", required = true)
        protected String type;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(byte[] value) {
            this.value = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "officePhone",
        "secondaryOfficePhone",
        "mobilePhone",
        "homePhone",
        "otherPhone"
    })
    public static class PhoneNumbers {

        @XmlElement(name = "office-phone", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String officePhone;
        @XmlElement(name = "secondary-office-phone", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String secondaryOfficePhone;
        @XmlElement(name = "mobile-phone", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String mobilePhone;
        @XmlElement(name = "home-phone", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String homePhone;
        @XmlElement(name = "other-phone", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected String otherPhone;

        public String getOfficePhone() {
            return officePhone;
        }

        public void setOfficePhone(String value) {
            this.officePhone = value;
        }

        public String getSecondaryOfficePhone() {
            return secondaryOfficePhone;
        }

        public void setSecondaryOfficePhone(String value) {
            this.secondaryOfficePhone = value;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String value) {
            this.mobilePhone = value;
        }

        public String getHomePhone() {
            return homePhone;
        }

        public void setHomePhone(String value) {
            this.homePhone = value;
        }

        public String getOtherPhone() {
            return otherPhone;
        }

        public void setOtherPhone(String value) {
            this.otherPhone = value;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "secondaryAddress"
    })
    public static class SecondaryAddresses {

        @XmlElement(name = "secondary-address", namespace = "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0")
        protected List<SecondaryAddress> secondaryAddress;

        public List<SecondaryAddress> getSecondaryAddress() {
            if (secondaryAddress == null) {
                secondaryAddress = new ArrayList<SecondaryAddress>();
            }
            return this.secondaryAddress;
        }

    }
}
