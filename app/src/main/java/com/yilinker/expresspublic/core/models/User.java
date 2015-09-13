package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

public class User
{
    @SerializedName(ApiKey.ID)
    private Long id;
    @SerializedName(ApiKey.USERNAME)
    private String username;
    @SerializedName(ApiKey.FIRST_NAME)
    private String firstname;
    @SerializedName(ApiKey.LAST_NAME)
    private String lastname;
    @SerializedName(ApiKey.FULL_NAME)
    private String fullname;
    @SerializedName(ApiKey.EMAIL)
    private String email;
    @SerializedName(ApiKey.CONTACT_NUMBER)
    private String contactNumber;

    public User() {
    }

    public User(Long id, String username, String firstname, String lastname, String fullname, String email, String contactNumber) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
