package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.List;

/**
 * Created by Jeico.
 */
public class ProvinceWithCity
{
    @SerializedName(ApiKey.ID)
    private Long id;
    @SerializedName(ApiKey.NAME)
    private String name;
    @SerializedName(ApiKey.CITY)
    private List<City> cityList;

    public ProvinceWithCity() {
    }

    public ProvinceWithCity(Long id, String name, List<City> cityList) {
        this.id = id;
        this.name = name;
        this.cityList = cityList;
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

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
