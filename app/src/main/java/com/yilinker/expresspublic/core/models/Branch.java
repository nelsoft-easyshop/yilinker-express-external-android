package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.Comparator;

/**
 * Created by Jeico.
 */
public class Branch
{
    @SerializedName(ApiKey.ID)
    public Long id;
    @SerializedName(ApiKey.NAME)
    public String name;
    @SerializedName(ApiKey.ADDRESS)
    public String address;
    @SerializedName(ApiKey.OPENING_TIME)
    public String openingTime;
    @SerializedName(ApiKey.CLOSING_TIME)
    public String closingTime;
    @SerializedName(ApiKey.CONTACT_NUMBER)
    public String contactNumber;
    @SerializedName(ApiKey.IMAGE)
    public String image;
    @SerializedName(ApiKey.LATITUDE)
    public Double latitude;
    @SerializedName(ApiKey.LONGITUDE)
    public Double longitude;
    public Float distance;

    public Branch() {
    }

    public Branch(Long id, String name, String address, String openingTime, String closingTime, String contactNumber, String image, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.contactNumber = contactNumber;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public static Comparator<Branch> getSortDistanceAsc() {
        return sortDistanceAsc;
    }

    public static void setSortDistanceAsc(Comparator<Branch> sortDistanceAsc) {
        Branch.sortDistanceAsc = sortDistanceAsc;
    }

    /**
     * Sort distance from current position to store position ASC
     */
    public static Comparator<Branch> sortDistanceAsc = new Comparator<Branch>()
    {
        @Override
        public int compare(Branch lhs, Branch rhs)
        {
            if(lhs.getDistance() > rhs.getDistance())
            {
                return 1;
            }
            else if(lhs.getDistance() < rhs.getDistance())
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    };
}
