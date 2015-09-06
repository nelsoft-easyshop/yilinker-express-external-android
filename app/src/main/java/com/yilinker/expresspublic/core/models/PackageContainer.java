package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Jeico.
 */
public class PackageContainer
{
    @SerializedName(ApiKey.WEIGHT)
    private String weight;
    @SerializedName(ApiKey.PACKAGE_CONTAINER)
    private String packageContainer;

    public PackageContainer() {
    }

    public PackageContainer(String weight, String packageContainer) {
        this.weight = weight;
        this.packageContainer = packageContainer;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPackageContainer() {
        return packageContainer;
    }

    public void setPackageContainer(String packageContainer) {
        this.packageContainer = packageContainer;
    }
}
