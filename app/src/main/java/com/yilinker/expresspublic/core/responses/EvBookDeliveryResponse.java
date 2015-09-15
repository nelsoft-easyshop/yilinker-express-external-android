package com.yilinker.expresspublic.core.responses;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.responses.bases.EvBaseResp;

import java.util.Date;

/**
 * Created by Jeico.
 */
public class EvBookDeliveryResponse extends EvBaseResp
{
    public Data data;

    public class Data
    {
        @SerializedName(ApiKey.BOOKING_NUMBER)
        public String bookingNumber;
        @SerializedName(ApiKey.SCHEDULE)
        public Date schedule;
        @SerializedName(ApiKey.QR_IMAGE)
        public String qrImage;
    }
}
