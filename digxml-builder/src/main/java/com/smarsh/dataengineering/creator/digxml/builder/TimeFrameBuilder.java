package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.model.digxml.TimeFrame;
import com.smarsh.dataengineering.model.digxml.TimeStamp;
import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public class TimeFrameBuilder implements AbstractBuilder<TimeFrameBuilder, TimeFrame> {
    @NotNull
    private final TimeStamp startTime;
    @NotNull
    private final TimeStamp endTime;

    public TimeFrameBuilder(@NotNull TimeStamp startTime, @NotNull TimeStamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeFrameBuilder(long startTimeMillis, long endTimeMillis) {
        startTime = new TimeStampBuilder(startTimeMillis).build();
        endTime = new TimeStampBuilder(endTimeMillis).build();
    }

    public TimeFrameBuilder(@NotNull String startTimeString, @NotNull String endTimeString, @NotNull SimpleDateFormat dateFormat) throws DateTimeUtils.InvalidDateException {
        startTime = new TimeStampBuilder(
                DateTimeUtils.toXmlGregorian(startTimeString, dateFormat)
        ).build();

        endTime = new TimeStampBuilder(
                DateTimeUtils.toXmlGregorian(endTimeString, dateFormat)
        ).build();
    }

    @Override
    public TimeFrame build() {
        var timeFrame = new TimeFrame();

        // TODO: sort and put in the correct order?
        timeFrame.setStartTime(startTime);
        timeFrame.setEndTime(endTime);

        return timeFrame;
    }
}
