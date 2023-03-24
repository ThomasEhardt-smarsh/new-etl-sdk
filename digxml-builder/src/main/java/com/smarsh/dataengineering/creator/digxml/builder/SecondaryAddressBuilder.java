package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.SecondaryAddress;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SecondaryAddressBuilder implements AbstractBuilder<SecondaryAddressBuilder, SecondaryAddress> {
    // REQUIRED
    @NotNull
    private String addressType;

    // OPTIONAL
    @Nullable
    private String value;

    public SecondaryAddressBuilder(@NotNull String addressType) {
        this.addressType = addressType;
    }

    @Override
    public SecondaryAddress build() {
        var secondaryAddress = new SecondaryAddress();

        // REQUIRED
        secondaryAddress.setAddressType(addressType);

        // OPTIONAL
        Optional.ofNullable(value).ifPresent(secondaryAddress::setValue);


        return secondaryAddress;
    }

    public void setAddressType(@NotNull String addressType) {
        this.addressType = addressType;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
    }
}
