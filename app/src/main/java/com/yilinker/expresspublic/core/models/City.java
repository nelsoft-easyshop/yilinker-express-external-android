package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.Comparator;

/**
 * Created by Jeico.
 */
public class City
{
    @SerializedName(ApiKey.ID)
//    private Long id;
    private String id;
//    @SerializedName(ApiKey.PROVINCE_ID)
//    private Long provinceId;
    @SerializedName(ApiKey.NAME)
    private String name;

    public City() {
    }

//    public City(Long id, Long provinceId, String name) {
//        this.id = id;
//        this.provinceId = provinceId;
//        this.name = name;
//    }

    public City(String id, String name) {
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

//    public Long getProvinceId() {
//        return provinceId;
//    }
//
//    public void setProvinceId(Long provinceId) {
//        this.provinceId = provinceId;
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
    public static Comparator<City> sortNameAsc = new Comparator<City>()
    {
        @Override
        public int compare(City lhs, City rhs)
        {
            return lhs.getName().compareTo(rhs.getName());
        }
    };
}
