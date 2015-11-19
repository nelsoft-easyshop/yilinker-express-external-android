package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Bryan on 11/18/2015.
 */


public class Schedule {

    @SerializedName(ApiKey.OPENING_HOUR)
    private String openingHour;
    @SerializedName(ApiKey.CLOSING_HOUR)
    private String closingHour;

    public Schedule() {
    }

    public Schedule(String openingHour, String closingHour) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }
}
