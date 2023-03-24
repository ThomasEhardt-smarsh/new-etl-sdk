package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.Groups;
import com.smarsh.dataengineering.model.digxml.NameType;
import com.smarsh.dataengineering.model.digxml.TimeStamp;
import com.smarsh.dataengineering.model.digxml.UserInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NameTypeBuilder implements AbstractBuilder<NameTypeBuilder, NameType> {
    @Nullable
    private String first;
    @Nullable
    private String middle;
    @Nullable
    private String last;
    @Nullable
    private String initials;
    @Nullable
    private String displayName;

    @Override
    public NameType build() {
        var nameType = new NameType();

        Optional.ofNullable(first).ifPresent(nameType::setFirst);
        Optional.ofNullable(middle).ifPresent(nameType::setMiddle);
        Optional.ofNullable(last).ifPresent(nameType::setLast);
        Optional.ofNullable(initials).ifPresent(nameType::setInitials);
        Optional.ofNullable(displayName).ifPresent(nameType::setDisplayName);

        return nameType;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
