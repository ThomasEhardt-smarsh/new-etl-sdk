package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.Groups;
import com.smarsh.dataengineering.model.NameType;
import com.smarsh.dataengineering.model.TimeStamp;
import com.smarsh.dataengineering.model.UserInfo;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class UserInfoBuilder implements AbstractBuilder<UserInfoBuilder, UserInfo> {
    @Nullable
    private String userId;

    @NotNull
    private final String userType;

    @Nullable
    private NameType name;

    @Nullable
    private String emailAddress;

    @Nullable
    private UserInfo.Affiliation affiliation;

    @Nullable
    private UserInfo.GeoLocation geoLocation;

    @Nullable
    private UserInfo.PhoneNumbers phoneNumbers;

    @Nullable
    private UserInfo.SecondaryAddresses secondaryAddresses;

    private final Groups groups = new Groups();

    @Nullable
    private UserInfo.Image image;

    @Nullable
    private TimeStamp modificationTime;


    public UserInfoBuilder(@NotNull String userType) {
        this.userType = userType;
    }

    @Override
    public UserInfo build() {
        UserInfo userInfo = new UserInfo();

        Optional.ofNullable(userId).ifPresent(userInfo::setUserId);

        userInfo.setUserType(userType);

        Optional.ofNullable(name).ifPresent(userInfo::setName);
        Optional.ofNullable(emailAddress).ifPresent(userInfo::setEmailAddress);
        Optional.ofNullable(affiliation).ifPresent(userInfo::setAffiliation);
        Optional.ofNullable(geoLocation).ifPresent(userInfo::setGeoLocation);
        Optional.ofNullable(phoneNumbers).ifPresent(userInfo::setPhoneNumbers);
        Optional.ofNullable(secondaryAddresses).ifPresent(userInfo::setSecondaryAddresses);

        if (!groups.getGroup().isEmpty()) {
            userInfo.setGroups(groups);
        }

        Optional.ofNullable(image).ifPresent(userInfo::setImage);
        Optional.ofNullable(modificationTime).ifPresent(userInfo::setModificationTime);

        return userInfo;
    }

    public void setUserId(@NotNull String userId) {
        this.userId = userId;
    }

    public void setName(@NotNull NameType name) {
        this.name = name;
    }

    public void setEmailAddress(@NotNull String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAffiliation(@NotNull UserInfo.Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public void setGeoLocation(@NotNull UserInfo.GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setPhoneNumbers(@NotNull UserInfo.PhoneNumbers phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setSecondaryAddresses(@NotNull UserInfo.SecondaryAddresses secondaryAddresses) {
        this.secondaryAddresses = secondaryAddresses;
    }

    public void addGroup(@NotNull String group) {
        groups.getGroup().add(group);
    }

    public void setImage(@NotNull UserInfo.Image image) {
        this.image = image;
    }

    public void setModificationTime(@NotNull TimeStamp modificationTime) {
        this.modificationTime = modificationTime;
    }
}
