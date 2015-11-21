package com.yilinker.expresspublic.core.requests;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvAddAddressLocationReq
{
    @SerializedName(ApiKey.FULL_NAME)
    public String fullName;
    @SerializedName(ApiKey.CONTACT_NUMBER)
    public String contactNumber;
    @SerializedName(ApiKey.TYPE)
    public List<Long> addressGroup;
    @SerializedName(ApiKey.UNIT)
    public String unitNumber;
    @SerializedName(ApiKey.BUILDING)
    public String buildingName;
    @SerializedName(ApiKey.STREET_NUMBER)
    public String streetNumber;
    @SerializedName(ApiKey.STREET)
    public String streetName;
    @SerializedName(ApiKey.VILLAGE)
    public String village;
    @SerializedName(ApiKey.PROVINCE_ID)
    public Long provinceId;
    @SerializedName(ApiKey.CITY_ID)
    public Long cityId;
    @SerializedName(ApiKey.BARANGAY_ID)
    public Long barangayId;
    @SerializedName(ApiKey.ZIPCODE)
    public String zipCode;
    @SerializedName(ApiKey.LATITUDE)
    public Double latitude;
    @SerializedName(ApiKey.LONGITUDE)
    public Double longitude;
    @SerializedName(ApiKey.ADDRESS_TYPE)
    public String addressType;
}
