package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Jeico.
 */
public class PickUpSchedule
{
    @SerializedName(ApiKey.ID)
    private Long id;
    @SerializedName(ApiKey.TIME_FROM)
    private String timeFrom;
    @SerializedName(ApiKey.TIME_TO)
    private String timeTo;
    @SerializedName(ApiKey.SCHEDULE)
    private String schedule;

    public PickUpSchedule() {
    }

    public PickUpSchedule(Long id, String timeFrom, String timeTo, String schedule) {
        this.id = id;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
