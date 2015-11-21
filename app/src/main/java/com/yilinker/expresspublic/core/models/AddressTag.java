package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by JBautista on 11/18/15.
 */
public class AddressTag {

    @SerializedName(ApiKey.VALUE)
    private int value;
    @SerializedName(ApiKey.NAME)
    private String name;

    public AddressTag() {
    }

    public AddressTag(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
