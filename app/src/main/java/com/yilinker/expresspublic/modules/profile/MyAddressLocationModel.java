package com.yilinker.expresspublic.modules.profile;

import com.yilinker.expresspublic.core.models.AddressGroup;
import com.yilinker.expresspublic.core.models.AddressLocation;

import java.util.List;

/**
 * Created by Jeico.
 */
public class MyAddressLocationModel
{
    private AddressGroup addressGroup;
    private List<AddressLocation> addressLocationList;

    public MyAddressLocationModel() {
    }

    public MyAddressLocationModel(AddressGroup addressGroup, List<AddressLocation> addressLocationList) {
        this.addressGroup = addressGroup;
        this.addressLocationList = addressLocationList;
    }

    public AddressGroup getAddressGroup() {
        return addressGroup;
    }

    public void setAddressGroup(AddressGroup addressGroup) {
        this.addressGroup = addressGroup;
    }

    public List<AddressLocation> getAddressLocationList() {
        return addressLocationList;
    }

    public void setAddressLocationList(List<AddressLocation> addressLocationList) {
        this.addressLocationList = addressLocationList;
    }
}
