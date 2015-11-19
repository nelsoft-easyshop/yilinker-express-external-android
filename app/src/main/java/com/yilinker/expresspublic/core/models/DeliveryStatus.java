package com.yilinker.expresspublic.core.models;

import com.google.gson.annotations.SerializedName;
import com.yilinker.expresspublic.core.contants.ApiKey;
import com.yilinker.expresspublic.core.utilities.DateUtils;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Jeico.
 */
public class DeliveryStatus
{
    @SerializedName(ApiKey.PACKAGE_STATUS)
    private String packageStatus;
//    @SerializedName(ApiKey.MESSAGE)
//    private String message;
    @SerializedName(ApiKey.DATE)
//    private Date date;
    private String date;

    public DeliveryStatus() {
    }

    public DeliveryStatus(String packageStatus, String date) {
        this.packageStatus = packageStatus;
        this.date = date;
    }

    public String getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }

    //    public DeliveryStatus(String message, Date date) {
//        this.message = message;
//        this.date = date;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

//    public Date getDate() {
//        return date;
//    }

    public String getDate() {
        return date;
    }

//    public void setDate(Date date) {
//        this.date = date;
//    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sort by date descending
     */
    public static Comparator<DeliveryStatus> sortDateDesc = new Comparator<DeliveryStatus>()
    {
        @Override
        public int compare(DeliveryStatus lhs, DeliveryStatus rhs)
        {
//            Date lhsDate = lhs.getDate();
//            Date rhsDate = rhs.getDate();
            Date lhsDate = DateUtils.parseDate(lhs.getDate());
            Date rhsDate = DateUtils.parseDate(rhs.getDate());

            return rhsDate.compareTo(lhsDate);
        }
    };
}
