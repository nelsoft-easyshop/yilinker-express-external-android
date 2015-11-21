package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

import java.util.Comparator;

/**
 * Created by Jeico.
 */
public class Province
{
    @SerializedName(ApiKey.ID)
//    private Long id;
    private String id;
    @SerializedName(ApiKey.NAME)
    private String name;

    public Province() {
    }

//    public Province(Long id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    public Province(String id, String name) {
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
    public static Comparator<Province> sortNameAsc = new Comparator<Province>()
    {
        @Override
        public int compare(Province lhs, Province rhs)
        {
            return lhs.getName().compareTo(rhs.getName());
        }
    };
}
