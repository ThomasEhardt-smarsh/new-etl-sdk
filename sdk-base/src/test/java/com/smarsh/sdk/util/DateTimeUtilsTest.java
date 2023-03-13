package com.smarsh.sdk.util;

import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeUtilsTest {

    public static Stream<Arguments> getTestValues() throws Exception {
        return Stream.of(
                Arguments.of(0L, createXMLGregorianCalendar(1970, Calendar.JANUARY, 1, 0, 0, 0, 0)),
                Arguments.of(946684800000L, createXMLGregorianCalendar(2000, Calendar.JANUARY, 1, 0, 0, 0, 0)),
                Arguments.of(253402300799999L, createXMLGregorianCalendar(9999, Calendar.DECEMBER, 31, 23, 59, 59, 999))
        );
    }

    @ParameterizedTest
    @MethodSource("getTestValues")
    void epochMillisToXmlGregorian(long epochMillis, XMLGregorianCalendar calendar) throws DateTimeUtils.InvalidDateException, DatatypeConfigurationException {
        assertEquals(calendar, DateTimeUtils.toXmlGregorian(epochMillis));
    }

    @ParameterizedTest
    @MethodSource("getTestValues")
    void xmlGregorianToEpochMillis(long epochMillis, XMLGregorianCalendar calendar) throws Exception {
        assertEquals(epochMillis, DateTimeUtils.toEpochMillis(calendar));
    }

    @Test
    void dateStringToEpochMillis() {
        var dateString = "2022-01-02T03:04:05.678Z";
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        var expected = 1641092645678L;

        assertEquals(expected, DateTimeUtils.toEpochMillis(dateString, format));
    }

    @Test
    void dateStringToEpochMillisMultiple() throws DateTimeUtils.InvalidDateException {
        var dateString = "2022-01-02T03:04:05.678Z";
        var formats = List.of(
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"),
                DateTimeFormatter.BASIC_ISO_DATE
        );

        var expected = 1641092645678L;
        assertEquals(expected, DateTimeUtils.toEpochMillis(dateString, formats));
    }

    @Test
    void fromDateString() throws Exception {
        var dateString = "2022-01-01T00:00:00.000Z";
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        var expected = DateTimeUtils.toXmlGregorian(1640995200000L);

        assertEquals(expected, DateTimeUtils.toXmlGregorian(dateString, dateFormat));
    }

    private static XMLGregorianCalendar createXMLGregorianCalendar(
            int year,
            int month,
            int day,
            int hour,
            int minute,
            int second,
            int millis
    ) throws DatatypeConfigurationException {
        var calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        calendar.setLenient(false);
        calendar.set(year, month, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, millis);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }
}