package com.yilinker.expresspublic.modules.findBranch;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeico.
 */
public class ProvinceModel implements ParentObject
{
    @SerializedName(ApiKey.ID)
    private Long id;
    @SerializedName(ApiKey.NAME)
    private String name;
    @SerializedName(ApiKey.CITY)
    private List<Object> cityList;

    public ProvinceModel(Long id, String name, List<Object> cityList) {
        this.id = id;
        this.name = name;
        this.cityList = cityList;
    }

    @Override
    public List<Object> getChildObjectList() {
        return cityList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        cityList = list;
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

    public List<Object> getCityList() {
        return cityList;
    }

    public void setCityList(List<Object> cityList) {
        this.cityList = cityList;
    }
}
