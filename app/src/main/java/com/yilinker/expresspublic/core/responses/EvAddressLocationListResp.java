package com.yilinker.expresspublic.core.responses;

import com.yilinker.expresspublic.core.models.AddressLocation;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvAddressLocationListResp extends EvBaseResp
{
    public List<AddressLocation> data;
}
