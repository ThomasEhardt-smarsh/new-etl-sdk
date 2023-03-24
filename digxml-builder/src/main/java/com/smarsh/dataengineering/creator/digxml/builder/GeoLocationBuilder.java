package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.UserInfo.GeoLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GeoLocationBuilder implements AbstractBuilder<GeoLocationBuilder, GeoLocation> {
    @Nullable
    private String city;
    @Nullable
    private String state;
    @Nullable
    private String country;

    @Override
    public GeoLocation build() {
        var geoLocation = new GeoLocation();

        Optional.ofNullable(city).ifPresent(geoLocation::setCity);
        Optional.ofNullable(state).ifPresent(geoLocation::setState);
        Optional.ofNullable(country).ifPresent(geoLocation::setCountry);

        return geoLocation;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
