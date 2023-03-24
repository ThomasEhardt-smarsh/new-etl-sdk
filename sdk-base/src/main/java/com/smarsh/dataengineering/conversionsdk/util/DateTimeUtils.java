package com.smarsh.dataengineering.conversionsdk.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.TimeZone;

public class DateTimeUtils {
    private static final Logger log = LoggerFactory.getLogger(DateTimeUtils.class);

    static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");

    private DateTimeUtils() {

    }

    public static long toEpochMillis(@NotNull final XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis();
    }

    public static XMLGregorianCalendar toXmlGregorian(final long epochMillis) throws InvalidDateException {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(DEFAULT_TIMEZONE);
        gregorianCalendar.setTimeInMillis(epochMillis);

        XMLGregorianCalendar xmlGregorianCalendar;

        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new InvalidDateException("unable to convert epoch millis [${epochMillis}] to XMLGregorianCalendar", e);
        }

        return xmlGregorianCalendar;
    }

    public static XMLGregorianCalendar toXmlGregorian(@NotNull final String dateString, @NotNull final SimpleDateFormat simpleDateFormat) throws InvalidDateException {
        try {
            return toXmlGregorian(simpleDateFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            throw new InvalidDateException(
                    "unable to parse %s with format %s".formatted(dateString, simpleDateFormat.toPattern()),
                    e
            );
        }
    }

    public static long toEpochMillis(final String dateString, final DateTimeFormatter formatter) throws InvalidDateException {
        if (Objects.isNull(dateString) || Objects.isNull(formatter)) {
            throw new InvalidDateException("dateString and formatter cannot be null");
        }

        try {
            return LocalDateTime.parse(dateString, formatter).toInstant(ZoneOffset.UTC).toEpochMilli();
        } catch (DateTimeParseException e) {
            throw new InvalidDateException(e.getMessage());
        }
    }

    public static long toEpochMillis(final String dateString, final Collection<DateTimeFormatter> formatters, long defaultValue) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return toEpochMillis(dateString, formatter);
            } catch (DateTimeParseException | InvalidDateException e) {
                // eat
            }
        }

        return defaultValue;
    }

    public static long toEpochMillis(@NotNull final String dateString, @NotNull final Collection<DateTimeFormatter> formatters) throws InvalidDateException {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return toEpochMillis(dateString, formatter);
            } catch (DateTimeParseException e) {
                // eat
            }
        }

        throw new InvalidDateException("cannot parse %s with any provided date formatters %s".formatted(dateString, formatters));
    }

    public static class InvalidDateException extends Exception {
        InvalidDateException(final String s, final Throwable t) {
            super(s, t);
        }

        public InvalidDateException(final String s) {
            super(s);
        }
    }
}
