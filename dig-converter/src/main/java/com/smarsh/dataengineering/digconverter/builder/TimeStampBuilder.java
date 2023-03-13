package com.smarsh.dataengineering.digconverter.builder;

import com.smarsh.dataengineering.model.TimeStamp;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Optional;

public class TimeStampBuilder implements AbstractBuilder<TimeStampBuilder, TimeStamp> {

    @Nullable
    private String description;

    @NotNull
    private final XMLGregorianCalendar xmlGregorianCalendar;

    public TimeStampBuilder(@NotNull XMLGregorianCalendar xmlGregorianCalendar) {
        this.xmlGregorianCalendar = xmlGregorianCalendar;
    }

    public TimeStampBuilder(@NotNull Long epochMillis) {
        try {
            var gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTimeInMillis(epochMillis);

            this.xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new IllegalArgumentException("cannot convert epoch millis " + epochMillis + " to an XMLGregorianCalendar");
        }
    }

    public TimeStampBuilder(@NotNull String timeString, @NotNull SimpleDateFormat dateFormat) throws DateTimeUtils.InvalidDateException {
        this.xmlGregorianCalendar = DateTimeUtils.toXmlGregorian(timeString, dateFormat);
    }

    @Override
    public TimeStamp build() {
        var timeStamp = new TimeStamp();

        Optional.ofNullable(description).ifPresent(timeStamp::setDescription);

        timeStamp.setTimestamp(this.xmlGregorianCalendar);

        return timeStamp;
    }
}