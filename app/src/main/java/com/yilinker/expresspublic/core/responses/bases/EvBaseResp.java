package com.yilinker.expresspublic.core.responses.bases;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;

/**
 * Created by Jeico on 9/1/2015.
 */
public class EvBaseResp
{
    @SerializedName(ApiKey.IS_SUCCESSFUL)
    public boolean success;
    @SerializedName(ApiKey.MESSAGE)
    public String message;
}
