package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.Comparator;
import java.util.List;

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
    //@SerializedName(ApiKey.OPENING_TIME)
    // Modified due to change of variable name
    public String openingHour;
    //public String openingTime;
    //@SerializedName(ApiKey.CLOSING_TIME)
    // Modified due to change of variable name
    public String closingHour;
    //public String closingTime;
    @SerializedName(ApiKey.CONTACT_NUMBER)
    public String contactNumber;
    @SerializedName(ApiKey.IMAGE)
    public String image;
    @SerializedName(ApiKey.LATITUDE)
    // Modified latitude datatype to String
    public String latitude;
    //public Double latitude;
    @SerializedName(ApiKey.LONGITUDE)
    // Modified longitude datatype to String
    public String longitude;
    //public Double longitude;
    public Float distance;

    // Added Schedule Model to handle list of schedule object
    @SerializedName(ApiKey.SCHEDULE)
    private List<Schedule> schedule;
    // Added Contact Model to handle list of contacts
    @SerializedName(ApiKey.CONTACTS)
    private  List<Contacts> contacts;

    public Branch() {
    }

    // Modified Constructor Mapping due to changes of latitude, longitude, schedules and contacts
    /*public Branch(Long id, String name, String address, String openingTime, String closingTime, String contactNumber, String image, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.contactNumber = contactNumber;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }*/

    public Branch(Long id, String name, String address, List<Schedule> schedule, List<Contacts> contacts, String contactNumber, String image, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contacts = contacts;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.schedule = schedule;
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
    // Modified due to change of variable name
    /*public String getOpeningTime() {
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
    }*/

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

    // Modified setter and getter due to change of datatype
    /*public Double getLatitude() {
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
    }*/

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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


    // Added Schedule List getter and setter
    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    // Added Contacts List getter and setter
    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
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
