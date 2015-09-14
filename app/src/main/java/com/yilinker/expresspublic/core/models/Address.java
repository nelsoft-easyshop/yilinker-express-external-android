package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Jeico.
 */
public class Address
{
    @SerializedName(ApiKey.ID)
    private Long id;
    @SerializedName(ApiKey.ADDRESS_ID)
    private Long addressId;
    @SerializedName(ApiKey.CONSUMER_ID)
    private Long consumerId;
    @SerializedName(ApiKey.CONTACT_PERSON)
    private String contactPerson;
    @SerializedName(ApiKey.CONTACT_PERSON_NUMBER)
    private String contactPersonNumber;
    @SerializedName(ApiKey.ADDRESS)
    private String address;
    @SerializedName(ApiKey.IS_PRIMARY)
    private Integer isPrimary;

    public Address() {
    }

    public Address(Long id, Long addressId, Long consumerId, String contactPerson, String contactPersonNumber, String address, Integer isPrimary) {
        this.id = id;
        this.addressId = addressId;
        this.consumerId = consumerId;
        this.contactPerson = contactPerson;
        this.contactPersonNumber = contactPersonNumber;
        this.address = address;
        this.isPrimary = isPrimary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }
}
