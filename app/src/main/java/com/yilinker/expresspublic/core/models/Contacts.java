package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Bryan on 11/18/2015.
 */
public class Contacts {

    @SerializedName(ApiKey.CONTACT_NUMBER)
    private String contactNumber;

    public Contacts() {
    }

    public Contacts(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
