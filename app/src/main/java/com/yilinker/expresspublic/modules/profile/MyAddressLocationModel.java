package com.yilinker.expresspublic.modules.profile;

import com.yilinker.expresspublic.core.models.AddressGroup;
import com.yilinker.expresspublic.core.models.Address;

import java.util.List;

/**
 * Created by Jeico.
 */
public class MyAddressLocationModel
{
    private AddressGroup addressGroup;
    private List<Address> addressList;

    public MyAddressLocationModel() {
    }

    public MyAddressLocationModel(AddressGroup addressGroup, List<Address> addressList) {
        this.addressGroup = addressGroup;
        this.addressList = addressList;
    }

    public AddressGroup getAddressGroup() {
        return addressGroup;
    }

    public void setAddressGroup(AddressGroup addressGroup) {
        this.addressGroup = addressGroup;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
