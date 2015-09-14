package com.yilinker.expresspublic.core.responses;

import com.yilinker.expresspublic.core.models.AddressGroup;
import com.yilinker.expresspublic.core.models.Address;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvMyAddressLocationModelListResp extends EvBaseResp
{
    public List<Data> data;

    public class Data
    {
        public AddressGroup group;
        public List<Address> address;
    }
}
