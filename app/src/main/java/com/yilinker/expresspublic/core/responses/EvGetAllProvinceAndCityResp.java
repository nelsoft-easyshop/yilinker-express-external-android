package com.yilinker.expresspublic.core.responses;

import com.yilinker.expresspublic.core.models.Province;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.List;

/**
 * Created by Jeico.
 */
public class EvGetAllProvinceAndCityResp extends EvBaseResp
{
    public List<Province> data;
}
