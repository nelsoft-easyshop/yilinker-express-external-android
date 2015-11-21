package com.yilinker.expresspublic.core.responses;

import com.yilinker.expresspublic.core.models.Address;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvAddressLocationListResp extends EvBaseResp
{
    public List<Address> data;
}
