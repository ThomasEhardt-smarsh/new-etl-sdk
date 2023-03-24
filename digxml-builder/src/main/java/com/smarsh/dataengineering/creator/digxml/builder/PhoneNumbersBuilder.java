package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.UserInfo;
import com.smarsh.dataengineering.model.digxml.UserInfo.PhoneNumbers;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PhoneNumbersBuilder implements AbstractBuilder<PhoneNumbersBuilder, PhoneNumbers> {
    @Nullable
    private String officePhone;
    @Nullable
    private String secondaryOfficePhone;
    @Nullable
    private String mobilePhone;
    @Nullable
    private String homePhone;
    @Nullable
    private String otherPhone;

    @Override
    public PhoneNumbers build() {
        var phoneNumbers = new PhoneNumbers();

        Optional.ofNullable(officePhone).ifPresent(phoneNumbers::setOfficePhone);
        Optional.ofNullable(secondaryOfficePhone).ifPresent(phoneNumbers::setSecondaryOfficePhone);
        Optional.ofNullable(mobilePhone).ifPresent(phoneNumbers::setMobilePhone);
        Optional.ofNullable(homePhone).ifPresent(phoneNumbers::setHomePhone);
        Optional.ofNullable(otherPhone).ifPresent(phoneNumbers::setOtherPhone);

        return phoneNumbers;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setSecondaryOfficePhone(String secondaryOfficePhone) {
        this.secondaryOfficePhone = secondaryOfficePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }
}
