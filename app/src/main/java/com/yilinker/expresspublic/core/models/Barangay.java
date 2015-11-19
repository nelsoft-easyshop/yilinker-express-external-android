package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.Comparator;

/**
 * Created by Jeico.
 */
public class Barangay
{
    @SerializedName(ApiKey.ID)
//    private Long id;
    private String id;
//    @SerializedName(ApiKey.CITY_ID)
//    private Long cityId;
    @SerializedName(ApiKey.NAME)
    private String name;

    public Barangay() {
    }

//    public Barangay(Long id, Long cityId, String name) {
//        this.id = id;
//        this.cityId = cityId;
//        this.name = name;
//    }

    public Barangay(String id, String name) {
        this.id = id;
        this.name = name;
    }


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Long getCityId() {
//        return cityId;
//    }
//
//    public void setCityId(Long cityId) {
//        this.cityId = cityId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Sort name ascending
     */
    public static Comparator<Barangay> sortNameAsc = new Comparator<Barangay>()
    {
        @Override
        public int compare(Barangay lhs, Barangay rhs)
        {
            return lhs.getName().compareTo(rhs.getName());
        }
    };
}
